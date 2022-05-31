package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.role.RoleResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserProfileResponse;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.dto.user.UserSummaryResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.exception.*;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        // biasanya ini untuk mengecek apakah ada username yang sama di database
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
    public UserProfileResponse getUserProfile(String username) {
        final User user = userRepository.getUserByName(username);
        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public UserSummaryResponse getCurrentUser(UserPrincipal currentUser) {
        return UserSummaryResponse.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .build();
    }

    @Override
    public UserResponse createAdmin(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(Instant.now());

        user.setRoles(new HashSet<>(Collections.singleton(
                roleRepository.findByName(RoleName.ADMIN)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)))));

        userRepository.save(user);
        return UserResponse.fromUser(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(Instant.now());

        Set<Role> roleSet = new HashSet<>();

        if (userRepository.count() == 0) {
            roleSet.add(roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

            roleSet.add(roleRepository.findByName(RoleName.USER)
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        }

        roleSet.add(roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        user.setRoles(roleSet);
        userRepository.save(user);

        return UserResponse.fromUser(user);
    }

    @Override
    public void addRoleToUser(String username, RoleName roleName) {

        final User user = userRepository.getUserByName(username);

        user.setRoles(new HashSet<>(Collections.singleton(
                roleRepository.findByName(roleName)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)))));

        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return UserResponse.fromUser(user);
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser) {

        final User user = userRepository.getUserByName(username);

        if (!Objects.equals(user.getId(), currentUser.getId()) && !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ADMIN.toString()))) {
            throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, "You don't have permission to update profile of: " + username));
        }

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);

        return UserResponse.fromUser(user);
    }

    @Override
    public ApiResponse deleteUser(String username, UserPrincipal currentUser) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", username));

        if (!Objects.equals(user.getId(), currentUser.getId()) && !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ADMIN.toString()))) {
            throw new AccessDeniedException(new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + username));
        }

        userRepository.deleteById(user.getId());
        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
    }

    @Override
    public ApiResponse removeAdmin(String username) {
        final User user = userRepository.getUserByName(username);

        user.setRoles(new HashSet<>(Collections.singleton(
                roleRepository.findByName(RoleName.ADMIN)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)))));

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
