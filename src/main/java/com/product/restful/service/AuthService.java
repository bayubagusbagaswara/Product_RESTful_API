package com.product.restful.service;

import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.RegisterRequest;
import com.product.restful.dto.user.UserDto;

public interface AuthService {

    UserDto signUp(RegisterRequest registerRequest);

    AuthenticationResponse signIn(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(LogoutRequest logoutRequest);
}
