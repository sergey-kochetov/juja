package com.juja.io;

import java.io.IOException;
import java.io.InputStream;

public class RuntimeTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ping ya.ru");
        int status = process.waitFor();
        String result;
        if (status == 0) {
            System.out.println("success");
            result = stringResult(process.getInputStream());
        } else {
            System.out.println("fail");
            result = stringResult(process.getErrorStream());
        }
        System.out.println(result);
    }

    private static String stringResult(InputStream input) throws IOException {
        StringBuilder result = new StringBuilder();
        byte[] buffer = new byte[512];
        while (input.available() != 0) {
            int count = input.read(buffer);
            if (count > 0) {
               result.append(new String(buffer));
            }
        }
        return result.toString();
    }
}
