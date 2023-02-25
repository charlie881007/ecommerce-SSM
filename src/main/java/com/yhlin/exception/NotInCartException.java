package com.yhlin.exception;

public class NotInCartException extends Exception {
    public NotInCartException() {
    }

    public NotInCartException(String message) {
        super(message);
    }
}
