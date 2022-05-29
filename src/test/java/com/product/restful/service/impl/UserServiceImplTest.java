package com.product.restful.service.impl;

import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    UserService userService;

    @Test
    void checkUsernameAvailability() {
        String username = "bayu123";
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        log.info("Username availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    void checkEmailAvailability() {
        String email = "bayu@gmail.com";
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);

        log.info("Email availability: {}", userIdentityAvailability.getAvailable());
    }
}