package com.yhlin.interceptor;

import com.yhlin.bean.User;
import com.yhlin.enums.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class VerifyPageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getStatus() == UserStatus.WAITING_FOR_EMAIL_CONFIRM) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }

        return false;
    }
}
