package com.gmail.vladyslavnicko.shop.exception;

public class ConflictException extends RuntimeException {
    private String message;

    public ConflictException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
