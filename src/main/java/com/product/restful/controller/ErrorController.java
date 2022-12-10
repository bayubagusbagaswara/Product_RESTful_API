package com.product.restful.controller;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.exception.AccessDeniedException;
import com.product.restful.exception.AppException;
import com.product.restful.exception.BadRequestException;
import com.product.restful.exception.RefreshTokenNotFoundException;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.exception.TokenRefreshException;
import com.product.restful.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getMessageResponse(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new WebResponse<>(Boolean.FALSE, "Bad Request", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> validatorHandler(ConstraintViolationException constraintViolationException) {
        return new ResponseEntity<>(new MessageResponse(Boolean.FALSE, constraintViolationException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> unauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<MessageResponse> accessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<MessageResponse> resolveException(TokenRefreshException exception) {
        return new ResponseEntity<>(new MessageResponse(Boolean.FALSE, exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageResponse> appException(AppException exception) {
        return new ResponseEntity<>(new MessageResponse(Boolean.FALSE, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RefreshTokenNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resfreshTokenNotFoundHandler(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return new ResponseEntity<>(new MessageResponse(Boolean.FALSE, refreshTokenNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> authenticationHandler(AuthenticationException authenticationException) {
        return new ResponseEntity<>(new MessageResponse(Boolean.FALSE, authenticationException.getMessage() + ", User is not authenticated. Username or password do not match"), HttpStatus.UNAUTHORIZED);
    }
}