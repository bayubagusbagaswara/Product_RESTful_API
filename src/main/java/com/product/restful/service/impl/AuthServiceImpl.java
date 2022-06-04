package com.product.restful.service.impl;

import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.*;
import com.product.restful.security.JwtTokenProvider;
import com.product.restful.service.AuthService;
import com.product.restful.service.RefreshTokenService;
import com.product.restful.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @Override
    public UserResponse signUp(SignUpRequest signUpRequest) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .build();

        return userService.createUser(createUserRequest);
    }

    @Override
    public AuthenticationResponse signIn(LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        final String token = jwtTokenProvider.generateTokenByUserId(userPrincipal);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken(userPrincipal.getId()).getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsernameOrEmail())
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyExpirationRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtTokenProvider.generateTokenFromUserId(refreshToken.getUser().getId());

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getJwtExpirationInMillis()))
                .username(refreshToken.getUser().getUsername())
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        userService.verifyUserByUsernameOrEmail(logoutRequest.getUsernameOrEmail());
        RefreshToken refreshToken = refreshTokenService.verifyExpirationRefreshToken(logoutRequest.getRefreshToken());
        refreshTokenService.deleteRefreshToken(refreshToken.getRefreshToken());
    }

}
