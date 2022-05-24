package com.product.restful.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BlogApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6593330219878485669L;

    private final HttpStatus status;
    private final String message;

    public BlogApiException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public BlogApiException(HttpStatus status, String message, Throwable exception) {
        super(exception);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
