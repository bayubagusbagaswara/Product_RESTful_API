package com.product.restful.service;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.*;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.UserPrincipal;

public interface UserService {

    UserIdentityAvailability checkUsernameAvailability(String username);
    UserIdentityAvailability checkEmailAvailability(String email);

    void checkUsernameIsExists(String username);

    void checkEmailIsExists(String email);

    UserProfileResponse getUserProfile(String username);

    UserSummaryResponse getCurrentUser(UserPrincipal currentUser);

    UserDto createAdmin(CreateUserRequest createUserRequest);

    UserDto createUser(CreateUserRequest createUserRequest);

    void addRoleToUser(String username, RoleName roleName);

    UserDto getUserById(Long id);

    ApiResponse deleteUser(String username);

    UserDto updateUser(String username, UpdateUserRequest updateUserRequest);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    void verifyUserByUsername(String username);

    void verifyUserByUsernameOrEmail(String usernameOrEmail);

    void verifyEmail(String email);
}
