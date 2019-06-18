package com.juja.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet("/shoping-cart")
public class ShopingCartServlet extends HttpServlet {
    private static final long serialVersionUID = -3423423L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("clear".equals(cmd)) {
            SessionUtils.clearCurrentShopingCart(req, resp);
        } else  if ("invalidate".equals(cmd)) {
            req.getSession().invalidate();
        } else if ("add".equals(cmd)) {
            addProduct(req, resp);
        } else {
            sync(req, resp);
        }
        showShoppingCart(req, resp);
    }

    protected void showShoppingCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SessionUtils.isCurrentShoppingCartCreated(req)) {
            resp.getWriter().println(SessionUtils.getCurrentShopingCart(req));
        } else {
            resp.getWriter().println("ShoppingCart is null");
        }
    }

    protected void addProduct(HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShopingCart(request);
        Random r = new Random();
        shoppingCart.addProduct(r.nextInt(2), r.nextInt(1) + 1);
    }

    protected void sync(HttpServletRequest request, HttpServletResponse response) {
        if (SessionUtils.isCurrentShoppingCartCreated(request)) {
            Cookie cookie = SessionUtils.findShoppingCartCookie(request);
            if (cookie != null) {
                ShoppingCart shoppingCart = shoppingCartFromString(cookie.getValue());
                SessionUtils.setCurrentShoppingCart(request, shoppingCart);
            }
        } else {
            ShoppingCart shoppingCart = SessionUtils.getCurrentShopingCart(request);
            Random r = new Random();
            shoppingCart.addProduct(r.nextInt(2), r.nextInt(1) + 1);
            String cookieValue = shoppingCartToString(shoppingCart);
            SessionUtils.updateCurrentShoppingCartCookie(cookieValue, response);
        }
    }
    // Serialization
    protected String shoppingCartToString(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            res.append(shoppingCartItem.getIdProduct()).append("-")
                    .append(shoppingCartItem.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }
    // Deserialization
    protected ShoppingCart shoppingCartFromString(String cookieValue) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = cookieValue.split("\\|");
        for (String item : items) {
            String data[] = item.split("-");
            try {
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addProduct(idProduct, count);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }
}
