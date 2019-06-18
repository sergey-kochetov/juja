package com.juja.servlet;

public class ShoppingCartItem {
    private int idProduct;
    private int count;
    public ShoppingCartItem(int idProduct, int count) {
        this.idProduct = idProduct;
        this.count = count;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getCount() {
        return count;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
