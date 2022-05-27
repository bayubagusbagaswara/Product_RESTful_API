package com.product.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException() {
    }

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
