package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.exception.AccessDeniedException;
import com.product.restful.exception.UnauthorizedException;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
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
    @Order(2)
    void checkUsernameAvailability() {
        String username = "bayu_bagaswara";
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        assertEquals(false, userIdentityAvailability.getAvailable());

        log.info("Username availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Order(3)
    void checkUsernameNotAvailability() {
        String username = "bayu123";
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        assertEquals(true, userIdentityAvailability.getAvailable());

        log.info("Username availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Order(4)
    void checkEmailAvailability() {
        String email = "bagaszwara12@gmail.com";
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);

        assertEquals(false, userIdentityAvailability.getAvailable());
        log.info("Email availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Order(5)
    void checkEmailNotAvailability() {
        String email = "bagaszwara@gmail.com";
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);

        assertEquals(true, userIdentityAvailability.getAvailable());
        log.info("Email availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Order(6)
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
    @Order(7)
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
    @Order(8)
    void createUser1() {
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .firstName("Issac")
                .lastName("Newton")
                .username("newton")
                .email("newton@gmail.com")
                .password("newton123")
                .build();

        UserResponse user1 = userService.createUser(createUserRequest1);
        assertNotNull(user1.getId());
        log.info("Role: {}", user1.getRoles()); // USER

        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .firstName("James")
                .lastName("Watt")
                .username("james_watt")
                .email("james@gmail.com")
                .password("james123")
                .build();

        UserResponse user2 = userService.createUser(createUserRequest2);
        assertNotNull(user2.getId());
        log.info("Role: {}", user2.getRoles()); // USER
    }

    @Test
    @Order(9)
    void updateUserRoleAdminToRoleUser() {
        // 1. Bayu [admin] akan mengubah data user Gosling
        String username = "gosling";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("Jamesss")
                .lastName("Gosling update")
                .username("james_gosling")
                .email("jamesupdate@gmail.com")
                .password("james12345")
                .build();

        User user = userRepository.getUserByName("bayu_bagaswara");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        UserResponse userResponse = userService.updateUser(username, updateUserRequest, currentUser);

        log.info("FirstName: {}", userResponse.getFirstName());
        log.info("Username: {}", userResponse.getUsername());
    }

    @Test
    @Order(10)
    void updateUserBySelf() {
        // 2. Albert akan mengubah data dirinya sendiri
        String username = "albert";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("Alberttt")
                .lastName("Einstein update")
                .username("einstein")
                .email("einstein@gmail.com")
                .password("einstein12345")
                .build();

        User user = userRepository.getUserByName("albert");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        UserResponse userResponse = userService.updateUser(username, updateUserRequest, currentUser);

        log.info("FirstName: {}", userResponse.getFirstName());
        log.info("Username: {}", userResponse.getUsername());
    }

    @Test
    @Order(11)
    void updateUserRoleUserToRoleAdminFailed() {
        // 3. Albert tidak bisa mengubah data Tesla
        String username = "tesla99";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("Nikolaaa")
                .lastName("Tesla update")
                .username("nikola")
                .email("nikola@gmail.com")
                .password("nikola12345")
                .build();

        User user = userRepository.getUserByName("newton");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        assertThrows(UnauthorizedException.class, () -> {
            UserResponse userResponse = userService.updateUser(username, updateUserRequest, currentUser);
        });
    }

    @Test
    @Order(12)
    void addRoleToUser() {
        // skenario menambahkan ROLE ADMIN ke user
        String username = "einstein";

        userService.addRoleToUser(username, RoleName.ADMIN);

        // find user by id, pastikan role user sudah ada ADMIN
        User user = userRepository.getUserByName(username);

        log.info("Role: {}", user.getRoles());
    }

    @Test
    @Order(13)
    void deleteUserByAdmin() {

        // admin Bayu menghapus user Gosling
        String username = "james_gosling";
        User user = userRepository.getUserByName("bayu_bagaswara");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        ApiResponse apiResponse = userService.deleteUser(username, currentUser);

        assertTrue(apiResponse.getSuccess());
        assertEquals(true, apiResponse.getSuccess());

        log.info("Success: {}", apiResponse.getSuccess());
        log.info("Message: {}", apiResponse.getMessage());
    }

    @Test
    @Order(14)
    void deleteUserBySelf() {

        // user Albert menghapus dirinya sendiri
        String username = "newton";
        User user = userRepository.getUserByName("newton");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        ApiResponse apiResponse = userService.deleteUser(username, currentUser);

        assertTrue(apiResponse.getSuccess());
        assertEquals(true, apiResponse.getSuccess());

        log.info("Success: {}", apiResponse.getSuccess());
        log.info("Message: {}", apiResponse.getMessage());
    }

    @Test
    @Order(15)
    void deleteUserToAdmin() {
        // user Newton menghapus Admin Bayu, ini akan gagal
        String username = "bayu_bagaswara";
        User user = userRepository.getUserByName("james_watt");

        UserPrincipal currentUser = UserPrincipal.createUserPrincipal(user);

        assertThrows(AccessDeniedException.class, () -> {
            ApiResponse apiResponse = userService.deleteUser(username, currentUser);
        });
    }
}