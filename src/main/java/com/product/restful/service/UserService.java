package com.product.restful.service;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserDto;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserProfileResponse;
import com.product.restful.dto.user.UserSummaryResponse;
import com.product.restful.entity.user.User;
import com.product.restful.entity.user.UserPrincipal;

public interface UserService {

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    void checkUsernameIsExists(String username);

    void checkEmailIsExists(String email);

    UserDto getUserByUsername(String username);

    UserProfileResponse getUserProfile(String username);

    UserSummaryResponse getCurrentUser(UserPrincipal currentUser);

    UserDto createAdmin(CreateUserRequest createUserRequest);

    UserDto createUser(CreateUserRequest createUserRequest);

    void addRoleToUser(String username, String roleName);

    UserDto getUserById(Long id);

    ApiResponse deleteUser(String username);

    UserDto updateUser(String username, UpdateUserRequest updateUserRequest);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    void verifyUserByUsername(String username);

    void verifyUserByUsernameOrEmail(String usernameOrEmail);

    void verifyEmail(String email);

    UserDto verifyResetPasswordLink(String uniqueCode);

    void verifyEmailAfterRegister(String uniqueCode);

    void setNewPassword(User user, String password);

    void forgotPassword(String email);
}
