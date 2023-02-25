package com.yhlin.exception;

public class UncancellableException extends Exception {
    public UncancellableException() {
    }

    public UncancellableException(String message) {
        super(message);
    }
}
