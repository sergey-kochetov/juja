package com.juja.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SessionUtils {
    public static ShoppingCart getCurrentShopingCart(HttpServletRequest req) {
        ShoppingCart shoppingCart = (ShoppingCart) req.getSession()
                .getAttribute(Constants.CURRENT_SHOPING_CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            setCurrentShoppingCart(req, shoppingCart);
        }
        return shoppingCart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.CURRENT_SHOPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(Constants.CURRENT_SHOPING_CART, shoppingCart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(Constants.CURRENT_SHOPING_CART);
        WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), null, 0, response);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest request) {
        return WebUtils.findCookie(request, Constants.Cookie.SHOPPING_CART.getName());
    }

    public static void clearCurrentShopingCart(HttpServletRequest req, HttpServletResponse resp) {

    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse response) {
        WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue,
                Constants.Cookie.SHOPPING_CART.getTtl(), response);
    }

    private SessionUtils() {}
}
