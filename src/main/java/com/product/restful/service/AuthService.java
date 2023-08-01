package com.product.restful.service;

import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest registerRequest);

    AuthenticationResponse login(LoginRequest loginRequest);

//    AuthenticationResponse createRefreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(LogoutRequest logoutRequest);
}
