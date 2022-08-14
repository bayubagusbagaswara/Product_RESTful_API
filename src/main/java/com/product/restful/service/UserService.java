package com.product.restful.service;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserDto;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.entity.user.User;

public interface UserService {

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    void checkUsernameIsExists(String username);

    void checkEmailIsExists(String email);

    UserDto getUserByUsername(String username);

    UserDto createAdmin(CreateUserRequest createUserRequest);

    UserDto createUser(CreateUserRequest createUserRequest);

    void addRoleToUser(String username, String roleName);

    UserDto getUserById(Long id);

    void deleteUser(String username);

    UserDto updateUser(String username, UpdateUserRequest updateUserRequest);

    void giveAdmin(String username);

    MessageResponse removeAdmin(String username);

    void verifyUserByUsername(String username);

    void verifyUserByUsernameOrEmail(String usernameOrEmail);

    void verifyEmail(String email);

    User getUser(Long userId);

    void verifyEmailActivation(String uniqueCode);

    UserDto verifyResetPasswordLink(String uniqueCode);

    void setNewPassword(User user, String password);

    void forgotPassword(String email);
}
