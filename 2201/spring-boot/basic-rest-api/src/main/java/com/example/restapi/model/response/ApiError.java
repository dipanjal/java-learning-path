package com.example.restapi.model.response;

public class ApiError {
    int code;
    String message;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
