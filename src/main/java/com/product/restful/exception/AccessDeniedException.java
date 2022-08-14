package com.product.restful.exception;

import com.product.restful.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {

    private MessageResponse messageResponse;
    private String message;

    public AccessDeniedException(MessageResponse messageResponse) {
        super();
        this.messageResponse = messageResponse;
    }

    public AccessDeniedException(String message) {
        super(message);
        this.message = message;
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageResponse getApiResponse() {
        return messageResponse;
    }

    public void setApiResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
