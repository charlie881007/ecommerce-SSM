package com.yhlin.exception;

public class InsufficientItemException extends Exception {
    public InsufficientItemException() {
    }

    public InsufficientItemException(String message) {
        super(message);
    }
}
