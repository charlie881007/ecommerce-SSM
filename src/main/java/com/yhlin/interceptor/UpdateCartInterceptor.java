package com.yhlin.interceptor;

import com.yhlin.bean.CartDetail;
import com.yhlin.bean.User;
import com.yhlin.service.ListingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class UpdateCartInterceptor implements HandlerInterceptor {
    @Autowired
    private ListingService listingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        for (CartDetail cartDetail : user.getCart().getCartDetails()) {
            cartDetail.setListing(listingService.getById(cartDetail.getListing().getId()));
        }
        return true;
    }
}
