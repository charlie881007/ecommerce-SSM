package com.yhlin.service.impl;

import com.yhlin.bean.*;
import com.yhlin.enums.CartStatus;
import com.yhlin.enums.ListingStatus;
import com.yhlin.enums.OrderStatus;
import com.yhlin.exception.*;
import com.yhlin.mapper.*;
import com.yhlin.service.ListingService;
import com.yhlin.util.EmailService;
import com.yhlin.util.Sha256Hasher;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements com.yhlin.service.UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Sha256Hasher sha256Hasher;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartDetailMapper cartDetailMapper;
    @Autowired
    private ListingService listingService;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ListingMapper listingMapper;

    @Override
    public User registerUser(RegisterForm registerForm) throws MessagingException {
        User user = new User(registerForm);

        // 寫入資料庫
        userMapper.insert(user);

        // 返回封裝物件
        return user;
    }

    @Override
    public User login(LoginForm loginForm) {
        // 加密密碼
        String encryptedPassword = sha256Hasher.hash(loginForm.getPassword());

        // 利用mapper查詢
        return userMapper.selectByEmailAndPassword(loginForm.getEmail(), encryptedPassword);
    }

    public String sendConfirmMail(String email) throws MessagingException {
        // 產生驗證碼
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString().substring(0, 6);

        // 寄驗證信
        emailService.sendMail(EmailService.username, email, "【驗證信】ecommerce 加入會員", code);

        return code;
    }

    @Override
    public boolean checkVerificationCode(User user, String verificationCode) {
        return user.getConfirmCode().equals(verificationCode);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public boolean checkEmailUsed(String email) {
        User user = userMapper.selectByEmail(email);
        return user != null;
    }

    @Override
    public void addItemToCart(User user, Listing targetItem, int quantity) throws ClosedListingException, DuplicateItemException, InsufficientItemException, DatabaseException {
        Cart cart = user.getCart();

        // 檢查商品是否已經下架
        boolean isClosed = targetItem.getStatus() == ListingStatus.CLOSED;
        if (isClosed) {
            throw new ClosedListingException();
        }

        // 檢查購物車內是否已經有此商品
        boolean isInCart = user.getCart().checkItemInCart(targetItem);
        if (isInCart) {
            throw new DuplicateItemException();
        }

        // 檢查商品數量是否足夠
        boolean isEnough = targetItem.getVolume() >= quantity;
        if (!isEnough) {
            throw new InsufficientItemException();
        }

        // 將商品放入購物車
        CartDetail cartDetail = cart.addItem(targetItem, quantity);

        // 將更新資訊寫入資料庫
        int res = cartDetailMapper.insert(cartDetail);
        if (res != 1) {
            throw new DatabaseException();
        }
    }

    @Override
    public void removeFromCart(User user, Listing targetItem) throws NotInCartException, DatabaseException {
        boolean isInCart = user.getCart().checkItemInCart(targetItem);
        if (!isInCart) {
            throw new NotInCartException();
        }

        // 將商品移出購物車
        CartDetail cartDetail = user.getCart().removeItem(targetItem);

        // 寫入資料庫
        int res = cartDetailMapper.delete(cartDetail);
        if (res != 1) {
            throw new DatabaseException();
        }
    }

    @Override
    public void removeClosedListingsFromCart(User user) throws DatabaseException {
        List<CartDetail> cartDetailList = user.getCart().getCartDetails();
        for (int i = cartDetailList.size() - 1; i >= 0; i--) {
            CartDetail cartDetail = cartDetailList.get(i);
            if (cartDetail.getListing().getStatus() == ListingStatus.CLOSED) {
                cartDetailList.remove(cartDetail);

                int res = cartDetailMapper.delete(cartDetail);
                if (res != 1) {
                    throw new DatabaseException();
                }
            }
        }
    }

    @Override
    public void reviseCart(User user, Integer listingId, Integer quantity) throws NotInCartException, DatabaseException {
        Cart cart = user.getCart();

        int res = 0;
        boolean inCart = false;

        for (CartDetail cartDetail : cart.getCartDetails()) {
            Listing item = cartDetail.getListing();
            if (item.getId().equals(listingId)) {
                cartDetail.setQuantity(quantity);
                res = cartDetailMapper.updateQuantity(cartDetail);

                if (res != 1) {
                    throw new DatabaseException();
                }

                inCart = true;
                break;
            }
        }

        if (!inCart) {
            throw new NotInCartException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkout(User user) throws EmptyCartException, InsufficientItemException, ClosedListingException, DatabaseException {
        Cart cart = user.getCart();
        //  檢查購物車是否為空
        if (cart.getCartDetails().size() == 0) {
            throw new EmptyCartException();
        }

        // 檢查購物車內是否有已下架的產品
        boolean hasClosedItem = hasClosedListing(cart);
        if (hasClosedItem) {
            throw new ClosedListingException();
        }

        // 檢查購物車內的商品數量是否都足夠
        boolean hasEnoughQuantity = hasEnoughQuantity(cart);
        if (!hasEnoughQuantity) {
            throw new InsufficientItemException();
        }

        // 商品數量都足夠，準備結帳
        int res = 0;

        // 更新物品可購買數量
        for (CartDetail cartDetail : cart.getCartDetails()) {
            Listing listing = cartDetail.getListing();
            int updatedQuantity = listing.getVolume() - cartDetail.getQuantity();
            listing.setVolume(updatedQuantity);
            res = listingService.updateListing(listing);
            if (res != 1) {
                throw new DatabaseException();
            }
        }

        // 建構Order物件
        Order order = new Order(user, cart);
        // 將order物件放入user物件
        user.addOrder(order);

        // 將order物件寫入資料庫
        res = orderMapper.insert(order);
        if (res != 1) {
            throw new DatabaseException();
        }

        // 關閉購物車，更新資料庫內的購物車資訊
        cart.setStatus(CartStatus.CLOSED);
        res = cartMapper.update(cart);
        if (res != 1) {
            throw new DatabaseException();
        }
        user.setCart(null);
    }

    private boolean hasClosedListing(Cart cart) {
        for (CartDetail cartDetail : cart.getCartDetails()) {
            if (cartDetail.getListing().getStatus() == ListingStatus.CLOSED) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnoughQuantity(Cart cart) {
        for (CartDetail cartDetail : cart.getCartDetails()) {
            if (cartDetail.getListing().getVolume() < cartDetail.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Order viewOrder(User user, Integer orderId) throws NoOrderFound {
        Order order = user.getOrder(orderId);

        if (order == null){
            throw new NoOrderFound();
        }

        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelOrder(User user, Integer orderId) throws NoOrderFound, UncancellableException, DatabaseException {
        Order order = user.getOrder(orderId);
        if (order == null) {
            throw new NoOrderFound();
        }

        if (order.getStatus() != OrderStatus.CREATED && order.getStatus() != OrderStatus.PREPARING) {
            throw new UncancellableException();
        }

        int res;
        order.setStatus(OrderStatus.CANCELED);
        res = orderMapper.update(order);

        if (res != 1) {
            throw new DatabaseException();
        }

        // 回充庫存
        List<CartDetail> cartDetails = order.getCart().getCartDetails();
        for (CartDetail cartDetail : cartDetails) {
            Listing item = listingService.getById(cartDetail.getListing().getId());
            int volumeBefore = item.getVolume();
            int volumeAfter = volumeBefore + cartDetail.getQuantity();
            item.setVolume(volumeAfter);
            res = listingMapper.update(item);

            if (res != 1) {
                throw new DatabaseException();
            }
        }
    }
}
