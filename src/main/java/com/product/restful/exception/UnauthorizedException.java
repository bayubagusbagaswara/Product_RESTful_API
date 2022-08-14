package com.product.restful.exception;

import com.product.restful.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private MessageResponse messageResponse;

    private String message;

    public UnauthorizedException(MessageResponse messageResponse) {
        super();
        this.messageResponse = messageResponse;
    }

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageResponse getApiResponse() {
        return messageResponse;
    }

    public void setApiResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
