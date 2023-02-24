package com.yhlin.exception;

public class CartRemoveException extends CartException {
    public CartRemoveException() {
    }

    public CartRemoveException(String message) {
        super(message);
    }
}
