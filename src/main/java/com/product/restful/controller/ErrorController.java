package com.product.restful.controller;

import com.product.restful.dto.WebResponse;
import com.product.restful.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public WebResponse<String> dataNotFoundHandler(ProductNotFoundException productNotFoundException) {
        return WebResponse.<String>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .data("Data not found")
                .build();
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public WebResponse<String> validatorHandler(ConstraintViolationException constraintViolationException) {
        return WebResponse.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .data(constraintViolationException.getMessage())
                .build();
    }
}