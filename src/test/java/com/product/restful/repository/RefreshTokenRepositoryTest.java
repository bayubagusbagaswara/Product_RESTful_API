package com.product.restful.repository;

import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("Token: {}", refreshToken.getRefreshToken());
    }

    @Test
    void findByUser() {
        String username = "bayu_bagaswara";
        User user = userRepository.getUserByName(username);
        RefreshToken refreshToken = refreshTokenRepository.getByUser(user).get();
        log.info("Token: {}", refreshToken.getRefreshToken());
    }

    @Test
    void deleteByUser() {
        String username = "bayu_bagaswara";
        User user = userRepository.getUserByName(username);
        refreshTokenRepository.deleteByUserId(user.getId());
    }
}