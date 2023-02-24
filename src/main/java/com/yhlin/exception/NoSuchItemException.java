package com.yhlin.exception;

public class NoSuchItemException extends CartException {
    public NoSuchItemException() {
    }

    public NoSuchItemException(String message) {
        super(message);
    }
}
