package com.product.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomUsernameNotFoundException extends UsernameNotFoundException {

    public CustomUsernameNotFoundException(String msg) {
        super(msg);
    }

    public CustomUsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
