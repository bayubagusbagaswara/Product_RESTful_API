package com.product.restful.service.impl;

import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.service.AuthService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static com.product.restful.entity.RoleName.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImplTest.class);

    @Autowired
    AuthService authService;

    @Test
    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Nikola");
        signUpRequest.setLastName("Tesla");
        signUpRequest.setUsername("tesla18");
        signUpRequest.setPassword("tesla123");
        signUpRequest.setEmail("tesla@gmail.com");

        UserResponse userResponse = authService.signUp(signUpRequest);

        assertNotNull(userResponse.getId());
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