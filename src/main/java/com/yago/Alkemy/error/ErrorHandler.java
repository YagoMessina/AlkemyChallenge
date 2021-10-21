package com.yago.Alkemy.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URISyntaxException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(URISyntaxException.class)
    private ResponseEntity<ApiError> handleURISyntaxExceptionException(URISyntaxException e){
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error.", "" + e.getMessage().substring(0, 30));
        ApiError error = new ApiError(apiException);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e){
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, "Invalid Argument", "" + e.getMessage().substring(0, 30));
        ApiError error = new ApiError(apiException);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, "Invalid Argument", "" + e.getAllErrors().get(0).getDefaultMessage());
        ApiError error = new ApiError(apiException);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, "Invalid Format", e.getMostSpecificCause().getMessage());
        ApiError error = new ApiError(apiException);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(ApiException.class)
    private ResponseEntity<ApiError> handleApiException(ApiException e) {
        ApiError error = new ApiError(e);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiError> handleUnknownException(Exception e) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Server error.",
            "Unknown error. " +
            "Exception message: " + e.getMessage() +
            "\nClass: " + e.getClass());

        return ResponseEntity.status(error.getCode()).body(error);
    }
}
