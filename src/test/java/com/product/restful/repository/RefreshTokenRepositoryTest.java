package com.product.restful.repository;

import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.user.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(RefreshTokenRepositoryTest.class);

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void getByRefreshToken() {
        String token = "caf14092-7da7-4e73-955d-db898d66639e";
        RefreshToken refreshToken = refreshTokenRepository.getByRefreshToken(token).get();
        assertNotNull(refreshToken);
        log.info("Token: {}", refreshToken.getRefreshToken());
    }

    @Test
    void deleteRefreshTokenByUserId() {
        String username = "bayu_bagaswara";
        User user = userRepository.getUserByName(username);
        refreshTokenRepository.delete(user.getRefreshToken());
        assertNull(user.getRefreshToken());
    }
}