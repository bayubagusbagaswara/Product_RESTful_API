package com.product.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResetPasswordInvalidException extends RuntimeException {

    public ResetPasswordInvalidException() {
    }

    public ResetPasswordInvalidException(String message) {
        super(message);
    }
}
