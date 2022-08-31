package com.product.restful.service.impl;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserDTO;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.entity.user.User;
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

        UserDTO user = userService.createUser(createUserRequest);
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

        UserDTO admin = userService.createAdmin(tesla);
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

        UserDTO user1 = userService.createUser(albert);
        assertNotNull(user1.getId());
        log.info("Role Albert: {}", user1.getRoles()); // USER

        UserDTO user2 = userService.createUser(newton);
        assertNotNull(user2.getId());
        log.info("Role Newton: {}", user2.getRoles()); // USER

        UserDTO user3 = userService.createUser(gosling);
        assertNotNull(user3.getId());
        log.info("Role Gosling: {}", user3.getRoles()); // USER

        UserDTO user4 = userService.createUser(watt);
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

        UserDTO userDto = userService.updateUser(username, updateUserRequest);
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

        UserDTO userDto = userService.updateUser(username, updateUserRequest);
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
        UserDTO user = userService.getUserByUsername(username);
        log.info("Role: {}", user.getRoles());
    }

    @Test
    @Disabled
    @Order(12)
    void deleteUserByAdmin() {
//        String username = "james_gosling";
//        MessageResponse messageResponse = userService.deleteUser(username);
//
//        assertTrue(messageResponse.getSuccess());
//        assertEquals(true, messageResponse.getSuccess());
//
//        log.info("Success: {}", messageResponse.getSuccess());
//        log.info("Message: {}", messageResponse.getMessage());
    }

    @Test
    @Disabled
    @Order(13)
    void deleteUserBySelf() {
//        String username = "newton";
//        MessageResponse messageResponse = userService.deleteUser(username);
//
//        assertTrue(messageResponse.getSuccess());
//        assertEquals(true, messageResponse.getSuccess());
//
//        log.info("Success: {}", messageResponse.getSuccess());
//        log.info("Message: {}", messageResponse.getMessage());
    }

    @Test
    @Disabled
    @Order(14)
    void deleteUserToAdmin() {
//        String username = "bayu_bagaswara";
//        assertThrows(AccessDeniedException.class, () -> {
//            MessageResponse messageResponse = userService.deleteUser(username);
//        });
    }

    @Test
    void createNewUser() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName("Bayu")
                .lastName("Bagaswara")
                .username("bagaswara")
                .email("bayu@gmail.com")
                .build();

        UserDTO user = userService.createUser(createUserRequest);
        assertNotNull(user.getId());
        log.info("Role: {}", user.getRoles()); // USER
    }

    @Test
    void testVerifyEmailAfterRegister() {
        String uniqueCode = "53ac9e63-bfba-48fe-b6a5-fa328101bc05";
        userService.verifyEmailActivation(uniqueCode);
    }

    @Test
    void testVerifyResetPasswordLink() {
        String uniqueCode = "4d06e5f3-bab8-4cc3-8efe-e89b01da9083";
        UserDTO userDto = userService.verifyResetPasswordLink(uniqueCode);
        assertNotNull(userDto);
    }

    @Test
    void testSetNewPassword() {
        String password = "B@gaswara_12";
        Long userId = 1L;
        User user = userService.getUser(userId);

        userService.setNewPassword(user, password);
    }

    @Test
    void testForgotPassword() {
        String email = "bayu@gmail.com";
        userService.forgotPassword(email);
    }
    // sehabis hit endpoint forgot password, maka kita harus hit endpoint verifyResetPasswordLink, lalu dilanjutnya set Password baru
    // password lama  $2a$11$ZO9xRDeA39rmsjgi8nk3CeSWayzAcWj3bNCbwZWXJrYpR/kGVvJrq
    // password baru  $2a$11$QJaVeRD8mm3rcPQGp43stu/5UjnIHLWkTgBeHRNxl7O3V/bFgIXou

    @Test
    void getUser() {
        Long userId = 2L;
        User user = userService.getUser(userId);
        System.out.println(user);
    }
}