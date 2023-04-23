package com.authentication.exception;

import org.springframework.http.HttpStatus;

public class CustomAuthenticationException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public CustomAuthenticationException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustomAuthenticationException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
