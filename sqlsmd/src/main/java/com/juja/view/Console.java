package com.juja.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void write(String message) {
        System.out.printf("%s%s", message, System.lineSeparator());
    }

    @Override
    public String read() {
        try {
        return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
