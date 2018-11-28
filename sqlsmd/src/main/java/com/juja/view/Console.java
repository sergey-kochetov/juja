package com.juja.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {

    @Override
    public void write(String message) {
        System.out.printf("%s%s", message, System.lineSeparator());
    }

    @Override
    public String read() {
        try {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
