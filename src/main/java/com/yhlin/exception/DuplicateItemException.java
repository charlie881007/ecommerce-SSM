package com.yhlin.exception;

public class DuplicateItemException extends Exception {
    public DuplicateItemException() {
    }

    public DuplicateItemException(String message) {
        super(message);
    }
}
