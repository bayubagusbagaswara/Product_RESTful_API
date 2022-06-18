package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserProfileResponse;
import com.product.restful.dto.user.UserDto;
import com.product.restful.dto.user.UserSummaryResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.exception.*;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserIdentityAvailability checkUsernameAvailability(String username) {
        // jika response bernilai true, maka username tidak ada di database
        // dan jika response bernilai false, maka username sudah ada di database
        return new UserIdentityAvailability(!userRepository.existsByUsername(username));
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        return new UserIdentityAvailability(!userRepository.existsByEmail(email));
    }

    @Override
    public void checkUsernameIsExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Username is already taken"));
        }
    }

    @Override
    public void checkEmailIsExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Email is already taken"));
        }
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.getUserByName(username);
        return UserDto.fromEntity(user);
    }

    @Override
    public UserProfileResponse getUserProfile(String username) {
        final User user = userRepository.getUserByName(username);
        return new UserProfileResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    @Override
    public UserSummaryResponse getCurrentUser(UserPrincipal currentUser) {
        return new UserSummaryResponse(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getEmail(), currentUser.getUsername());
    }

    @Override
    public UserDto createAdmin(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = new User(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getUsername(),
                passwordEncoder.encode(userRequest.getPassword()),
                Instant.now()
        );

        user.setRoles(new HashSet<>(Collections.singleton(
                roleRepository.getByName(RoleName.ADMIN.name())
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)))));

        userRepository.save(user);
        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = new User(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getUsername(),
                passwordEncoder.encode(userRequest.getPassword()),
                Instant.now()
        );

        Set<Role> roleSet = new HashSet<>();

        if (userRepository.count() == 0) {
            roleSet.add(roleRepository.getByName(RoleName.ADMIN.name())
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

            roleSet.add(roleRepository.getByName(RoleName.USER.name())
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        }

        roleSet.add(roleRepository.getByName(RoleName.USER.name())
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        user.setRoles(roleSet);
        userRepository.save(user);

        return UserDto.fromEntity(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        final User user = userRepository.getUserByName(username);
        user.addRole(roleRepository.getByName(roleName)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto updateUser(String username, UpdateUserRequest updateUserRequest) {
        final User user = userRepository.getUserByName(username);
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return UserDto.fromEntity(user);
    }

    @Override
    public ApiResponse deleteUser(String username) {
        final User user = userRepository.getUserByName(username);
        userRepository.deleteById(user.getId());
        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
    }

    @Override
    public ApiResponse giveAdmin(String username) {
        final User user = userRepository.getUserByName(username);
        user.addRole(roleRepository.getByName(RoleName.ADMIN.name())
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + username);
    }

    @Override
    public ApiResponse removeAdmin(String username) {
        final User user = userRepository.getUserByName(username);
        user.removeRole(roleRepository.getByName(RoleName.ADMIN.name())
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You took ADMIN role from user: " + username);
    }

    @Override
    public void verifyUserByUsername(String username) {
        userRepository.getUserByName(username);
    }

    @Override
    public void verifyUserByUsernameOrEmail(String usernameOrEmail) {
        userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
    }

    @Override
    public void verifyEmail(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

}
