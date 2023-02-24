package com.yhlin.exception;

public class AddItemException extends CartException {
    public AddItemException() {
    }

    public AddItemException(String message) {
        super(message);
    }
}
