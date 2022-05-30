package com.product.restful.service;

import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.User;

public interface AuthService {

    UserResponse signUp(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(LogoutRequest logoutRequest);
}
