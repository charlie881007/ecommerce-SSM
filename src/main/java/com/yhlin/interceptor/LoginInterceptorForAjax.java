package com.yhlin.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

public class LoginInterceptorForAjax implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {

            String redirectURL = "/login" +
                    "?redirect=" + request.getHeader("Referer");


            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("msg", "redirect");
            res.put("url", redirectURL);
            PrintWriter out;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();

            return false;
        }

        return true;
    }
}
