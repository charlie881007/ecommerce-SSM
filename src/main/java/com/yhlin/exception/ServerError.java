package com.yhlin.exception;

public class ServerError extends Exception {
    public ServerError() {
    }

    public ServerError(String message) {
        super(message);
    }
}
