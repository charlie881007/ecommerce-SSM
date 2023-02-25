package com.yhlin.errorHandler;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class MyErrorHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public String handleMismatch() {
        return "error";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public String handleMethodNotSupport() {
        return "error";
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public String handleArgumentNotProvide() {
        return "error";
    }
}
