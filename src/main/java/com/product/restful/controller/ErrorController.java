package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.exception.AccessDeniedException;
import com.product.restful.exception.AppException;
import com.product.restful.exception.BadRequestException;
import com.product.restful.exception.CustomUsernameNotFoundException;
import com.product.restful.exception.RefreshTokenNotFoundException;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.exception.TokenRefreshException;
import com.product.restful.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getApiResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> validatorHandler(ConstraintViolationException constraintViolationException) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, constraintViolationException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> unauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> accessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> resolveException(TokenRefreshException exception) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse> appException(AppException exception) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RefreshTokenNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> resfreshTokenNotFoundHandler(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, refreshTokenNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomUsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> usernameNotFoundHandler(CustomUsernameNotFoundException usernameNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, usernameNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> authenticationHandler(AuthenticationException authenticationException) {
        return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, authenticationException.getMessage() + ", User is not authenticated"), HttpStatus.UNAUTHORIZED);
    }
}