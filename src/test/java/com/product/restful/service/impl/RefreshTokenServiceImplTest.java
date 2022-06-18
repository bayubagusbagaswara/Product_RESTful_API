package com.product.restful.service.impl;

import com.product.restful.dto.refreshToken.RefreshTokenResponse;
import com.product.restful.entity.RefreshToken;
import com.product.restful.exception.RefreshTokenNotFoundException;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(RefreshTokenServiceImplTest.class);

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Test
    void generateRefreshToken() {
        Long userId = 1L;
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.generateRefreshToken(userId);
        log.info("Token: {}", refreshTokenResponse.getRefreshToken());
    }

    @Test
    void validateRefreshToken() {
        String refreshToken = "";
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.validateRefreshToken(refreshToken);
        assertEquals(refreshToken, refreshTokenResponse.getRefreshToken());
    }

    @Test
    void getRefreshToken() {
        String refreshToken = "";
        RefreshToken token = refreshTokenService.getRefreshToken(refreshToken);
        assertEquals(refreshToken, token.getRefreshToken());
    }

    @Test
    void verifyExpirationRefreshToken() {
        String refreshToken = "";
        RefreshToken token = refreshTokenService.verifyExpirationRefreshToken(refreshToken);
        assertNotNull(token);
        assertEquals(refreshToken, token.getRefreshToken());
    }

    @Test
    void deleteRefreshToken() {
        String refreshToken = "";
        refreshTokenService.deleteRefreshToken(refreshToken);
        assertThrows(RefreshTokenNotFoundException.class, () -> {
            RefreshToken token = refreshTokenService.getRefreshToken(refreshToken);
        });
    }
}