package com.juja;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static String changeXYold(String input) {
        char[] chars = input.toCharArray();
                char[] result = new char[chars.length];
        for (int index = 0; index < chars.length; index++) {
            if (chars[index] == 'x') {
                result[index] = 'y';
            } else {
               result[index] = chars[index];
            }
        }
        return new String(result);
    }
    public static String changeXY(String input) {
        if (input.length() == 0) {
            return input;
        }
        String part = "";
        if (input.length() > 1) {
           part = changeXY(input.substring(1));
        }

       char ch = input.charAt(0);
       char newCh = (ch == 'x' ? 'y' : ch);
       return newCh + part;
    }

//    public static void main(String[] args) {
//
//        String s1 = "A1B2C3";
//        Stream<String> stream = Arrays.stream(s1.replace("[a-zA-Z]", " ").split(" "));
//        AtomicInteger result = new AtomicInteger();
//        System.out.println(stream.collect(Collectors.joining()));
//        stream.peek(n -> {
//            System.out.println(n);
//            result.set(Integer.valueOf(n));
//        });
//        System.out.println(result.toString());
//
//    }

}
