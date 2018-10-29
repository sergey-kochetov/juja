package com.juja.string;

public class StringUtils {
    public static String rightShift(String arg, int delta) {
        if (arg.length() == 0 || delta == 0) {
            return arg;
        }
        char[] result = arg.toCharArray();
        delta = delta % arg.length();
        if (delta < 0) {
            delta *= -1;
            while (delta > 0) {
                char startVar = result[0];
                for(int counter = arg.length() - 1; counter >= 0; counter--)
                {
                    char curVal = result[counter];
                    result[counter] = startVar;
                    startVar = curVal;
                }
                delta--;
            }
        } else {
            while(delta > 0) {
                char lastVar = result[arg.length() - 1];
                for(int counter = 0; counter < arg.length(); counter++)
                {
                    char curVal = result[counter];
                    result[counter] = lastVar;
                    lastVar = curVal;
                }
                delta--;
            }
        }
        return String.valueOf(result);
    }
}
