package com.product.restful.controller;

import com.product.restful.dto.WebResponse;
import com.product.restful.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public WebResponse<String> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
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