package com.yago.Alkemy.error;

import org.springframework.http.HttpStatus;

public class ApiError {

    private int code;
    private String error;
    private String message;

    public ApiError(ApiException e) {
        this.code = e.getCode().value();
        this.error = e.getError();
        this.message = e.getMessage();
    }

    public ApiError(HttpStatus code, String error, String message) {
        this.code = code.value();
        this.error = error;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
