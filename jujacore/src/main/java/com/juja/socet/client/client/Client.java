package com.juja.socet.client.client;

public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {

            SimpleClient sc = new SimpleClient(i);
            sc.start();
        }
    }
}
