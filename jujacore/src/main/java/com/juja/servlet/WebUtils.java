package com.juja.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class WebUtils {
    public static void setCookie(String name, Object o, int i, HttpServletResponse response) {

    }

    public static Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    if (cookie.getValue() != null && !"".equals(cookie.getValue())) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

    public static void setCookie(String name, String value, int age, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(age);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private WebUtils() {}
}
