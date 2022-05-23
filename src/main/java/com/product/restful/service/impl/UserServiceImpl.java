package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.CreateUserRequest;
import com.product.restful.dto.UserResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.exception.AppException;
import com.product.restful.exception.BadRequestException;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(CreateUserRequest userRequest) {

        // cek apakah username sudah ada atau belum
        if (userRepository.existsByUsername(userRequest.getUsername())) {

            ApiResponse apiResponse = ApiResponse.builder()
                    .success(Boolean.FALSE)
                    .message("Username is already taken")
                    .build();
            throw new BadRequestException(apiResponse);
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            // jika email sudah ada
            ApiResponse apiResponse = ApiResponse.builder()
                    .success(Boolean.FALSE)
                    .message("Email is already taken")
                    .build();
            throw new BadRequestException(apiResponse);
        }

        // create roles untuk user baru
        Set<Role> roles = new HashSet<>();
        roles.add(
                roleRepository.findByName(RoleName.STAFF.getRoleName())
                        .orElseThrow(() -> new AppException("User role not set")));

        // create object User
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(roles)
                .build();

        // save user
        final User userSaved = userRepository.save(user);

        // response
        return UserResponse.builder()
                .id(userSaved.getId())
                .firstName(userSaved.getFirstName())
                .lastName(userSaved.getLastName())
                .username(userSaved.getUsername())
                .email(userSaved.getEmail())
                .password(userSaved.getPassword())
                .roles(userSaved.getRoles())
                .build();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        // cari user by username dulu
        final User user = userRepository.getUserByName(username);

        Set<Role> roles = new HashSet<>();

        // cari role berdasarkan role
        final Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException("User role not set"));
        roles.add(role);

        // masukkan role kedalam user
        user.setRoles(roles);

        // simpan user
        userRepository.save(user);
    }
}
