package com.product.restful.service;

import com.product.restful.dto.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.entity.User;

public interface AuthService {

    User signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(LoginRequest loginRequest);

    // refresh token
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    // logout
}
