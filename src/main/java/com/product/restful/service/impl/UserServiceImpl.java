package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.exception.*;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
                roleRepository.findByName(RoleName.MEMBER)
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

        // response
        return mapUserToUserResponse(userRepository.save(user));
    }

    @Override
    public void addRoleToUser(String username, RoleName roleName) {
        final User user = userRepository.getUserByName(username);

        Set<Role> roles = new HashSet<>();

        final Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException("User role not set"));

        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return mapUserToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser) {

        final User user = userRepository.getUserByName(username);

        if (user.getId().equals(currentUser.getId()) ||
                currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ADMIN.toString()))) {

            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            user.setUsername(updateUserRequest.getUsername());
            user.setEmail(updateUserRequest.getEmail());

            return mapUserToUserResponse(userRepository.save(user));
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .success(Boolean.FALSE)
                .message("You don't have permission to update profile of: " + username)
                .build();
        // lalu lempar error
        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteUser(String username, UserPrincipal currentUser) {

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", username));

        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ADMIN.getRoleName()))) {

            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + username);
            throw new AccessDeniedException(apiResponse);
        }

        userRepository.deleteById(user.getId());

        return ApiResponse.builder()
                .success(Boolean.TRUE)
                .message("You successfully deleted profile of: " + username)
                .build();
    }

    @Override
    public ApiResponse removeAdmin(String username) {
        final User user = userRepository.getUserByName(username);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.MEMBER)
                .orElseThrow(() -> new AppException("User role not set")));

        user.setRoles(roles);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(Boolean.TRUE)
                .message("You took ADMIN role from user: " + username)
                .build();
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
