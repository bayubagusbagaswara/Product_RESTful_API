package com.product.restful.service;

import com.product.restful.dto.CreateUserRequest;
import com.product.restful.dto.UserResponse;

public interface UserService {

    // create new User
    // saat create user pertama kali, maka secara otomatis role nya adalah staff

     UserResponse createUser(CreateUserRequest createUserRequest);

    // user admin, dapat menambahkan role to User
    // void addRoleToUser(String username, String roleName)
}
