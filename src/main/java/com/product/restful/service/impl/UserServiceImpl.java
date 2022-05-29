package com.product.restful.service.impl;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.*;
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

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        final Boolean isAvailable = !userRepository.existsByUsername(username);
        return UserIdentityAvailability.builder()
                .available(isAvailable)
                .build();
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return UserIdentityAvailability.builder()
                .available(isAvailable)
                .build();
    }

    @Override
    public void checkUsernameIsExists(String username) {
        if (userRepository.existsByUsername(username)) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .success(Boolean.FALSE)
                    .message("Username is already taken")
                    .build();
            throw new BadRequestException(apiResponse);
        }
    }

    @Override
    public void checkEmailIsExists(String email) {
        if (userRepository.existsByEmail(email)) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .success(Boolean.FALSE)
                    .message("Email is already taken")
                    .build();
            throw new BadRequestException(apiResponse);
        }
    }

    @Override
    public UserProfileResponse getUserProfile(String username) {
        return null;
    }

    @Override
    public UserSummaryResponse getCurrentUser(String username) {
        return null;
    }

    @Override
    public UserResponse createAdmin(CreateUserRequest createUserRequest) {
        return null;
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
        return UserResponse.mapToDto(user);
    }

    @Override
    public void addRoleToUser(String username, RoleName roleName) {

        final User user = userRepository.getUserByName(username);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return UserResponse.mapToDto(user);
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest updateUserRequest, UserPrincipal currentUser) {

        final User user = userRepository.getUserByName(username);

        if (!Objects.equals(user.getId(), currentUser.getId()) ||
                !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ADMIN.toString()))) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .success(Boolean.FALSE)
                    .message("You don't have permission to update profile of: " + username)
                    .build();
            throw new UnauthorizedException(apiResponse);
        }

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());

        userRepository.save(user);

        return UserResponse.mapToDto(user);
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
        roles.add(roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new AppException("User role not set")));

        user.setRoles(roles);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(Boolean.TRUE)
                .message("You took ADMIN role from user: " + username)
                .build();
    }

    @Override
    public void verifyUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

}
