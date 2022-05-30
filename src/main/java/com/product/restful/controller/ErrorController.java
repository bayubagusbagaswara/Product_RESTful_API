package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.exception.*;
import org.springframework.http.HttpStatus;
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
    public WebResponse<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
        ApiResponse apiResponse = resourceNotFoundException.getApiResponse();
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public WebResponse<ApiResponse> validatorHandler(ConstraintViolationException constraintViolationException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(constraintViolationException.getMessage());
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(apiResponse)
                .build();
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public WebResponse<ApiResponse> unauthorizedException(UnauthorizedException exception) {
        ApiResponse apiResponse = exception.getApiResponse();
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public WebResponse<ApiResponse> badRequestException(BadRequestException exception) {
        ApiResponse apiResponse = exception.getApiResponse();
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public WebResponse<ApiResponse> accessDeniedException(AccessDeniedException exception) {
        ApiResponse apiResponse = exception.getApiResponse();
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public WebResponse<ApiResponse> resolveException(TokenRefreshException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.FORBIDDEN);
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public WebResponse<ApiResponse> appException(AppException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @ExceptionHandler(value = RefreshTokenNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public WebResponse<ApiResponse> resfreshTokenNotFoundHandler(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(refreshTokenNotFoundException.getMessage());
        apiResponse.setStatus(HttpStatus.NOT_FOUND);

        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .data(apiResponse)
                .build();
    }
}