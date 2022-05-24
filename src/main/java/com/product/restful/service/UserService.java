package com.product.restful.service;

import com.product.restful.dto.CreateUserRequest;
import com.product.restful.dto.UserResponse;

public interface UserService {

     UserResponse createUser(CreateUserRequest createUserRequest);

    // user admin, dapat menambahkan role to User
     void addRoleToUser(String username, String roleName);

     // get user by id
    UserResponse getUserById(Long id);

    // menghapus user hanya bisa dilakukan oleh MANAGER

    // update user bisa dilakukan jika user berhasil terautentikasi (semua role bisa update)
    // tapi tidak bisa mengupdate ROLE
}
