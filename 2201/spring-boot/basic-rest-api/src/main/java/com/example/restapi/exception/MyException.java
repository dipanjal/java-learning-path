package com.example.restapi.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {

    private final HttpStatus status;

    public MyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
