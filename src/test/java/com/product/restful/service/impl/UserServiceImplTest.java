package com.product.restful.service.impl;

import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserResponse;
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
    void createUserFirst() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("Bayu");
        createUserRequest.setLastName("Bagaswara");
        createUserRequest.setUsername("bayu_bagaswara");
        createUserRequest.setPassword("B@gaswara12");
        createUserRequest.setEmail("bagaszwara12@gmail.com");

        UserResponse user = userService.createUser(createUserRequest);

        assertNotNull(user.getId());
        log.info("Role: {}", user.getRoles()); // USER, ADMIN
    }

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

    @Test
    void createAdmin() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName("Nikola")
                .lastName("Tesla")
                .username("tesla99")
                .email("tesla@gmail.com")
                .password("tesla123")
                .build();

        UserResponse admin = userService.createAdmin(createUserRequest);

        assertNotNull(admin.getId());
        log.info("Role: {}", admin.getRoles()); // ADMIN
    }

    @Test
    void createUser() {
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .firstName("Albert")
                .lastName("Einstein")
                .username("albert")
                .email("albert@gmail.com")
                .password("albert123")
                .build();

        UserResponse user1 = userService.createUser(createUserRequest1);
        assertNotNull(user1.getId());
        log.info("Role: {}", user1.getRoles()); // USER

        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .firstName("James")
                .lastName("Gosling")
                .username("gosling")
                .email("gosling@gmail.com")
                .password("gosling123")
                .build();

        UserResponse user2 = userService.createUser(createUserRequest2);
        assertNotNull(user2.getId());
        log.info("Role: {}", user2.getRoles()); // USER
    }

    @Test
    void updateUser() {
        // hanya bisa dilakukan oleh user yang memiliki ROLE ADMIN
        // atau user yang bersangkutan sendiri, artinya username dan userRequest harus sama

        // 1. Bayu [admin] akan mengubah user albert

    }
}