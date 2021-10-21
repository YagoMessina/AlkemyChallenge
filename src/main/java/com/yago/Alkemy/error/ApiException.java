package com.yago.Alkemy.error;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private HttpStatus code;
    private String error;

    public ApiException(HttpStatus code, String error, String message) {
        super(message);
        this.code = code;
        this.error = error;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
