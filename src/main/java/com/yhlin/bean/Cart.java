package com.yhlin.bean;

import com.yhlin.enums.CartStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {
    private Integer id;

    private User user;

    private Date createTime;

    private CartStatus status;

    private List<CartDetail> cartDetails;

    public Cart() {
        this.cartDetails = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CartDetail addItem(Listing item, int amount) {
        CartDetail cartDetail = new CartDetail(item, amount, this);
        cartDetails.add(cartDetail);
        return cartDetail;
    }

    public CartDetail removeItem(Listing targetItem) {
        for (int i = cartDetails.size() - 1; i >= 0; i--) {
            CartDetail cartDetail = cartDetails.get(i);
            if (targetItem.getId().equals(cartDetail.getListing().getId())) {
                cartDetails.remove(cartDetail);
                return cartDetail;
            }
        }

        return null;
    }

    public boolean checkItemInCart(Listing targetItem) {
        for (CartDetail cartDetail : getCartDetails()) {
            if (cartDetail.getListing().getId().equals(targetItem.getId())) {
                return true;
            }
        }
        return false;
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartDetail cartDetail : cartDetails) {
            int subtotal = cartDetail.getListing().getPrice() * cartDetail.getQuantity();
            total += subtotal;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", createTime=" + createTime +
                ", status=" + status +
                ", cartDetailList=" + cartDetails +
                '}';
    }
}