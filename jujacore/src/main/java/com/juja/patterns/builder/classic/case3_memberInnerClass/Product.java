package com.juja.patterns.builder.classic.case3_memberInnerClass;

public class Product {
    private String field1;
    private String field2;
    private String field3;

    private Product() {
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

   public static Builder builder() {
        return new Product().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder field1(String field1) {
            Product.this.field1 = field1;
            return this;
        }
        public Builder field2(String field2) {
            Product.this.field1 = field2;
            return this;
        }
        public Builder field3(String field3) {
            Product.this.field1 = field3;
            return this;
        }

        public Product build() {
            return Product.this;
        }
    }

}
