package com.yhlin.exception;

public class DuplicateItemException extends CartException {
    public DuplicateItemException() {
    }

    public DuplicateItemException(String message) {
        super(message);
    }
}
