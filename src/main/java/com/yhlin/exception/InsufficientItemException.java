package com.yhlin.exception;

public class InsufficientItemException extends CartException {
    public InsufficientItemException() {
    }

    public InsufficientItemException(String message) {
        super(message);
    }
}
