package com.product.restful.service;

import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.UserPrincipal;

public interface UserService {

     UserResponse createUser(CreateUserRequest createUserRequest);

    // user admin, dapat menambahkan role to User
     void addRoleToUser(String username, String roleName);

     // get user by id
    UserResponse getUserById(Long id);

    // menghapus user hanya bisa dilakukan oleh MANAGER
    void deleteUser(String username, UserPrincipal currentUser);

    // update user bisa dilakukan jika user berhasil terautentikasi (semua role bisa update)
    // tapi tidak bisa mengupdate ROLE
    UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser);

    // delete user
}
