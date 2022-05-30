package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        UserResponse userResponse = authService.signUp(signUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(userResponse.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }

    @PostMapping("/login")
    public WebResponse<AuthenticationResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authService.signIn(loginRequest);
        return WebResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/refresh/token")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_USER"})
    @PreAuthorize("hasRole('MEMBER')")
    public WebResponse<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse authenticationResponse = authService.refreshToken(refreshTokenRequest);
        return WebResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('MEMBER')")
    public WebResponse<String> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return WebResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data("Refresh Token Deleted Successfully")
                .build();
    }

}
