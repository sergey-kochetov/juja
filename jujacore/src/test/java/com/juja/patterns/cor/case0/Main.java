package com.juja.patterns.cor.case0;

public class Main {

    public static void main(String[] args) {
        Handler h1 = new Handler(String.class);
        Handler h2 = new Handler(Boolean.class);
        Handler h3 = new Handler(Integer.class);

        h1.setSuccessor(h2);
        h2.setSuccessor(h3);

        h1.handlerRequest(new Request("qwe"));
        h1.handlerRequest(new Request(true));
        h1.handlerRequest(new Request(1));
    }
}
