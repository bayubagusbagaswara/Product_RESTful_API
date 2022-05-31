package com.product.restful.service.impl;

import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.service.AuthService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImplTest.class);

    @Autowired
    AuthService authService;

    @Test
    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Bayu");
        signUpRequest.setLastName("Bagaswara");
        signUpRequest.setUsername("bayu_bagaswara");
        signUpRequest.setPassword("B@gaswara12");
        signUpRequest.setEmail("bagaszwara12@gmail.com");

        UserResponse userResponse = authService.signUp(signUpRequest);

        assertNotNull(userResponse.getId());
        log.info("User: {}", userResponse.getRoles());
    }

    @Test
    void signIn() {
        String usernameOrEmail = "bagaszwara12@gmail.com";
        String password = "B@gaswara12";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(usernameOrEmail);
        loginRequest.setPassword(password);

        AuthenticationResponse authenticationResponse = authService.signIn(loginRequest);

        assertNotNull(authenticationResponse.getAccessToken());
        assertNotNull(authenticationResponse.getRefreshToken());
        assertNotNull(authenticationResponse.getExpiresAt());

        log.info("Access Token: {}", authenticationResponse.getAccessToken());
        log.info("Refresh Token: {}", authenticationResponse.getRefreshToken());
        log.info("Expires at: {}", authenticationResponse.getExpiresAt());
        log.info("Token Type: {}", authenticationResponse.getTokenType());
        log.info("Username: {}", authenticationResponse.getUsername());
    }

    @Test
    void refreshToken() {
        String refreshToken = "0b0e48e8-edbd-4978-8dce-85932fe18d24";
        String username = "bayu_bagaswara";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(refreshToken);
        refreshTokenRequest.setUsername(username);

        AuthenticationResponse authenticationResponse = authService.refreshToken(refreshTokenRequest);

        assertNotNull(authenticationResponse.getAccessToken());
        assertNotNull(authenticationResponse.getRefreshToken());
        assertNotNull(authenticationResponse.getExpiresAt());
        log.info("Access Token: {}", authenticationResponse.getAccessToken());
        log.info("Refresh Token: {}", authenticationResponse.getRefreshToken());
        log.info("Expires at: {}", authenticationResponse.getExpiresAt());
        log.info("Username: {}", authenticationResponse.getUsername());

    }

    @Test
    void logout() {
        String refreshToken = "0b0e48e8-edbd-4978-8dce-85932fe18d24";
        String username = "";
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername(username);
        logoutRequest.setRefreshToken(refreshToken);

        authService.logout(logoutRequest);
    }
}