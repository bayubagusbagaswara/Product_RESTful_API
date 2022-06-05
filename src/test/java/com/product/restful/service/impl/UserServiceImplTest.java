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
import org.junit.jupiter.api.*;
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
    void createFirstUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Bayu",
                "Bagaswara",
                "bayu_bagaswara",
                "B@gaswara12",
                "bagaszwara12@gmail.com"
        );

        UserResponse user = userService.createUser(createUserRequest);
        assertNotNull(user.getId());
        assertEquals(2, user.getRoles().size());
        log.info("Role: {}", user.getRoles()); // USER, ADMIN
    }

    @Test
    @Order(2)
    void createAdmin() {
        CreateUserRequest tesla = new CreateUserRequest(
                "Nikola",
                "Tesla",
                
                "tesla99",
                "tesla123",
                "tesla@gmail.com");

        UserResponse admin = userService.createAdmin(tesla);
        assertNotNull(admin.getId());
        log.info("Role: {}", admin.getRoles()); // ADMIN
    }

    @Test
    @Order(3)
    void createUser() {
        CreateUserRequest albert = new CreateUserRequest(
                "Albert",
                "Einstein",
                "albert",
                "albert123",
                "albert@gmail.com");

        CreateUserRequest gosling = new CreateUserRequest(
                "James",
                "Gosling",
                "gosling",
                "gosling123",
                "gosling@gmail.com");

        CreateUserRequest newton = new CreateUserRequest(
                "Issac",
                "Newton",
                "newton",
                "newton123",
                "newton@gmail.com");

        CreateUserRequest watt = new CreateUserRequest(
                "James",
                "Watt",
                "james_watt",
                "james123",
                "james@gmail.com");

        UserResponse user1 = userService.createUser(albert);
        assertNotNull(user1.getId());
        log.info("Role Albert: {}", user1.getRoles()); // USER

        UserResponse user2 = userService.createUser(newton);
        assertNotNull(user2.getId());
        log.info("Role Newton: {}", user2.getRoles()); // USER

        UserResponse user3 = userService.createUser(gosling);
        assertNotNull(user3.getId());
        log.info("Role Gosling: {}", user3.getRoles()); // USER

        UserResponse user4 = userService.createUser(watt);
        assertNotNull(user4.getId());
        log.info("Role Watt: {}", user4.getRoles()); // USER
    }

    @Test
    @Disabled
    @Order(4)
    void checkUsernameAvailability() {
        String username = "bayu_bagaswara";
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        assertEquals(false, userIdentityAvailability.getAvailable());
        log.info("Username availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Disabled
    @Order(5)
    void checkUsernameNotAvailability() {
        String username = "bayu123";
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        assertEquals(true, userIdentityAvailability.getAvailable());
        log.info("Username availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Disabled
    @Order(6)
    void checkEmailAvailability() {
        String email = "bagaszwara12@gmail.com";
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);

        assertEquals(false, userIdentityAvailability.getAvailable());
        log.info("Email availability: {}", userIdentityAvailability.getAvailable());
    }

    @Test
    @Disabled
    @Order(7)
    void checkEmailNotAvailability() {
        String email = "bagaszwara@gmail.com";
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);

        assertEquals(true, userIdentityAvailability.getAvailable());
        log.info("Email availability: {}", userIdentityAvailability.getAvailable());
    }


    @Test
    @Disabled
    @Order(8)
    void updateUserRoleAdminToRoleUser() {
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
    @Disabled
    @Order(9)
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
    @Disabled
    @Order(10)
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
    @Disabled
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
    @Disabled
    @Order(12)
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
    @Disabled
    @Order(13)
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
    @Disabled
    @Order(14)
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