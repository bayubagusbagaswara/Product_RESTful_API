package com.product.restful.service.impl;

import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.auth.RegisterRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.user.UserDTO;
import com.product.restful.service.AuthService;
import org.junit.jupiter.api.Disabled;
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
        RegisterRequest registerRequest = new RegisterRequest(
                "Bayu",
                "Bagaswara",
                "bayu_bagaswara",
                "B@gaswara12",
                "bagaszwara12@gmail.com");

        UserDTO userDto = authService.signUp(registerRequest);

        assertNotNull(userDto.getId());
        log.info("User: {}", userDto.getRoles());
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
    @Disabled
    void refreshToken() {
        String refreshToken = "0b0e48e8-edbd-4978-8dce-85932fe18d24";
        String username = "bayu_bagaswara";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(refreshToken);

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
    @Disabled
    void logout() {
        String refreshToken = "0b0e48e8-edbd-4978-8dce-85932fe18d24";
        String usernameOrEmail = "";
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsernameOrEmail(usernameOrEmail);
        logoutRequest.setRefreshToken(refreshToken);

        authService.logout(logoutRequest);
    }
}