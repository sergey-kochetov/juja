package com.juja.servlet.exxception;

public class ValidationException extends IllegalArgumentException {
    private static final long serialVersionUID = -312L;
    public ValidationException(String s) {
        super(s);
    }
}
