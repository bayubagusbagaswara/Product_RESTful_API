package com.product.restful.service.impl;

import com.product.restful.dto.auth.SignUpRequest;
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
        signUpRequest.setFirstName("Issaac");
        signUpRequest.setLastName("Newton");
        signUpRequest.setUsername("newton55");
        signUpRequest.setPassword("newton123");
        signUpRequest.setEmail("newton@gmail.com");

        UserResponse userResponse = authService.signUp(signUpRequest);

        assertNotNull(userResponse.getId());
        log.info("User: {}", userResponse.getRoles());
    }

    @Test
    void signIn() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void logout() {
    }
}