package com.juja.patterns.state.classic.case0_from;

public class Main {


    public static void main(String[] args) {
        Context context = new Context();

        context.request("data1");
        context.request("data2");
        context.request("data3");
        context.request("data4");
        context.request("data5");
    }
}
