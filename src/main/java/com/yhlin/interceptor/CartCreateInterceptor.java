package com.yhlin.interceptor;

import com.yhlin.bean.Cart;
import com.yhlin.bean.User;
import com.yhlin.enums.CartStatus;
import com.yhlin.mapper.CartMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Date;

public class CartCreateInterceptor implements HandlerInterceptor {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 檢查用戶有沒有購物車
        User user = (User) request.getSession().getAttribute("user");
        Cart cart = user.getCart();

        // 沒有的話創建購物車
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCreateTime(new Date());
            cart.setStatus(CartStatus.OPEN);

            user.setCart(cart);
            int res = cartMapper.insert(cart);

            if (res != 1) {
                try {
                    request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
                    return false;
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }
}
