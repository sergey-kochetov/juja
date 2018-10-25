package com.juja;

public class Main {

    public static void main(String[] args) {

        String input = "codex";
        String expected = "codey";

        assetMethod(input, expected);

    }

    private static void assetMethod(String input, String expected) {
        String actual = changeXY(input);
        String staus = actual.equals("codey") ?
                "Ok" :
                "FAIL expected: " + expected + " but was: '" + actual + "'";
        System.out.println(staus);
    }

    public static String changeXY(String input) {
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

}
