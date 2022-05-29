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

    UserResponse createAdmin(CreateUserRequest createUserRequest);

    UserResponse createUser(CreateUserRequest createUserRequest);

    void addRoleToUser(String username, RoleName roleName);

    UserResponse getUserById(Long id);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser);

    ApiResponse removeAdmin(String username);

    void verifyUser(Long id);
}
