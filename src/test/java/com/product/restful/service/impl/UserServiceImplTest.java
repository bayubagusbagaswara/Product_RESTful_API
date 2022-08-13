package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserDto;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.exception.AccessDeniedException;
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

        UserDto user = userService.createUser(createUserRequest);
        assertNotNull(user.getId());
        assertEquals(2, user.getRoles().size());
        log.info("Role: {}", user.getRoles()); // USER, ADMIN
    }

    @Test
    void createNewUser() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName("Cristiano")
                .lastName("Ronaldo")
                .username("cristiano")
                .email("cristiano@gmail.com")
                .build();

        UserDto user = userService.createUser(createUserRequest);
        assertNotNull(user.getId());
//        assertEquals(2, user.getRoles().size());
        log.info("Role: {}", user.getRoles()); // USER
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

        UserDto admin = userService.createAdmin(tesla);
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

        UserDto user1 = userService.createUser(albert);
        assertNotNull(user1.getId());
        log.info("Role Albert: {}", user1.getRoles()); // USER

        UserDto user2 = userService.createUser(newton);
        assertNotNull(user2.getId());
        log.info("Role Newton: {}", user2.getRoles()); // USER

        UserDto user3 = userService.createUser(gosling);
        assertNotNull(user3.getId());
        log.info("Role Gosling: {}", user3.getRoles()); // USER

        UserDto user4 = userService.createUser(watt);
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

        UserDto userDto = userService.updateUser(username, updateUserRequest);
        log.info("FirstName: {}", userDto.getFirstName());
        log.info("Username: {}", userDto.getUsername());
    }

    @Test
    @Disabled
    @Order(9)
    void updateUserBySelf() {
        String username = "albert";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("Alberttt")
                .lastName("Einstein update")
                .username("einstein")
                .email("einstein@gmail.com")
                .password("einstein12345")
                .build();

        UserDto userDto = userService.updateUser(username, updateUserRequest);
        log.info("FirstName: {}", userDto.getFirstName());
        log.info("Username: {}", userDto.getUsername());
    }

    @Test
    @Disabled
    @Order(10)
    void updateUserRoleUserToRoleAdminFailed() {
        String username = "tesla99";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("Nikolaaa")
                .lastName("Tesla update")
                .username("nikola")
                .email("nikola@gmail.com")
                .password("nikola12345")
                .build();
    }

    @Test
    @Disabled
    @Order(12)
    void addRoleToUser() {
        String username = "einstein";
        userService.addRoleToUser(username, RoleName.ADMIN.name());
        UserDto user = userService.getUserByUsername(username);
        log.info("Role: {}", user.getRoles());
    }

    @Test
    @Disabled
    @Order(12)
    void deleteUserByAdmin() {
        String username = "james_gosling";
        ApiResponse apiResponse = userService.deleteUser(username);

        assertTrue(apiResponse.getSuccess());
        assertEquals(true, apiResponse.getSuccess());

        log.info("Success: {}", apiResponse.getSuccess());
        log.info("Message: {}", apiResponse.getMessage());
    }

    @Test
    @Disabled
    @Order(13)
    void deleteUserBySelf() {
        String username = "newton";
        ApiResponse apiResponse = userService.deleteUser(username);

        assertTrue(apiResponse.getSuccess());
        assertEquals(true, apiResponse.getSuccess());

        log.info("Success: {}", apiResponse.getSuccess());
        log.info("Message: {}", apiResponse.getMessage());
    }

    @Test
    @Disabled
    @Order(14)
    void deleteUserToAdmin() {
        String username = "bayu_bagaswara";
        assertThrows(AccessDeniedException.class, () -> {
            ApiResponse apiResponse = userService.deleteUser(username);
        });
    }
}