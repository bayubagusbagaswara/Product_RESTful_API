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

import java.util.ArrayList;
import java.util.List;

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
        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.STAFF)
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
}
