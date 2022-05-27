package com.product.restful.service;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.UserPrincipal;

public interface UserService {

     UserResponse createUser(CreateUserRequest createUserRequest);

     void addRoleToUser(String username, String roleName);

    UserResponse getUserById(Long id);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser);

    ApiResponse removeAdmin(String username);
}
