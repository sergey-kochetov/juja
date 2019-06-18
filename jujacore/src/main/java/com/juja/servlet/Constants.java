package com.juja.servlet;

public final class Constants {
    public static final String CURRENT_SHOPING_CART = "CURRENT_SHOPING_CART";
    public static final int MAX_PRODUCT_COUNT_PER_SHOPING_CART = 10;
    public static final int MAX_PRODUCT_PER_SHOPING_CART = 20;

    public enum Cookie {
        //1 year ttl
        SHOPPING_CART("iSCC", 60 * 60 * 24 * 365);

        private final String name;
        private final int ttl;

        Cookie(String name, int ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }
    }
}
