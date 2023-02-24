package com.yhlin.interceptor;

import com.yhlin.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginAndRegisterPageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 用戶還沒登入，可以造訪登入頁面與註冊頁面
        if (user == null) {
            return true;
        }

        // 用戶已經登入過了，重新導向至首頁
        response.sendRedirect(request.getContextPath() + "/");
        return false;
    }
}
