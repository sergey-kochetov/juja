package com.juja.servlet;

import com.juja.servlet.exxception.ValidationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 123L;
    private Map<Integer, ShoppingCartItem> products = new HashMap<>();
    private int totalCount = 0;

    public void addProduct(int idProduct, int count) {
        validateShopingCartSize(idProduct);
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem == null) {
            validateProductCount(count);
            shoppingCartItem = new ShoppingCartItem(idProduct, count);
            products.put(idProduct, shoppingCartItem);
        } else {
            int newCount = shoppingCartItem.getCount() + count;
            validateProductCount(newCount);
            shoppingCartItem.setCount(newCount);
        }
        refreshStatistics();
    }

    public void removeProduct(Integer idProduct, int count) {
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {

            }
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Collection<ShoppingCartItem> getItems() {
        return products.values();
    }

    private void refreshStatistics() {
        totalCount = 0;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
        }
    }

    private void validateProductCount(int count) {
        if (count > Constants.MAX_PRODUCT_COUNT_PER_SHOPING_CART) {
            throw new ValidationException("Limit for product reached: count=" + count);
        }
    }

    private void validateShopingCartSize(int idProduct) {
        if (products.size() > Constants.MAX_PRODUCT_PER_SHOPING_CART
                || ((products.size() == Constants.MAX_PRODUCT_PER_SHOPING_CART)
                && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for ShopingCart size reached: size=" + products.size());
        }
    }

}
