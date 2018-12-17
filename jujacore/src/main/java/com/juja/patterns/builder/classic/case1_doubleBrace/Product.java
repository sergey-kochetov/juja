package com.juja.patterns.builder.classic.case1_doubleBrace;

public class Product {
    private final String field1;
    private final String field2;
    private final String field3;

    private Product(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    @Override
    public String toString() {
        return String.format("Product{field1='%s', field2='%s', field3='%s'}",
                field1, field2, field3);
    }

    public static class Builder {
        protected String field1 = "default1";
        protected String field2 = "default2";
        protected String field3 = "default3";

        public Product build() {
            return new Product(field1, field2, field3);
        }
    }
}
