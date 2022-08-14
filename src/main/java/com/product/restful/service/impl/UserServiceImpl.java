package com.product.restful.service.impl;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserIdentityAvailability;
import com.product.restful.dto.user.UserDTO;
import com.product.restful.entity.Role;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.entity.user.ResetPassword;
import com.product.restful.entity.user.User;
import com.product.restful.entity.user.UserPassword;
import com.product.restful.exception.*;
import com.product.restful.repository.ResetPasswordRepository;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserPasswordRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserPasswordRepository userPasswordRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ResetPasswordRepository resetPasswordRepository, UserPasswordRepository userPasswordRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.resetPasswordRepository = resetPasswordRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

    @Override
    public UserIdentityAvailability checkUsernameAvailability(String username) {
        return new UserIdentityAvailability(!userRepository.existsByUsername(username));
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        return new UserIdentityAvailability(!userRepository.existsByEmail(email));
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public void checkUsernameIsExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException(new MessageResponse(Boolean.FALSE, "Username is already taken"));
        }
    }

    @Override
    public void checkEmailIsExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(new MessageResponse(Boolean.FALSE, "Email is already taken"));
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.getUserByName(username);
        return UserDTO.fromEntity(user);
    }

    @Override
    public UserDTO createAdmin(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        user.setRoles(new HashSet<>(Collections.singleton(
                roleRepository.getByName(RoleName.ADMIN.name())
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)))));

        userRepository.save(user);
        return UserDTO.fromEntity(user);
    }

    @Override
    public UserDTO createUser(CreateUserRequest userRequest) {
        checkUsernameIsExists(userRequest.getUsername());
        checkEmailIsExists(userRequest.getEmail());

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setUserActive(false);

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

        UserPassword userPassword = new UserPassword();
        userPassword.setUser(user);
        user.setUserPassword(userPassword);

        userRepository.save(user);
        userPasswordRepository.save(userPassword);

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setUser(user);
        resetPasswordRepository.save(resetPassword);

        return UserDTO.fromEntity(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.getUserByName(username);
        user.addRole(roleRepository.getByName(roleName).orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return UserDTO.fromEntity(user);
    }

    @Override
    public UserDTO updateUser(String username, UpdateUserRequest updateUserRequest) {
        User user = userRepository.getUserByName(username);
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        userRepository.save(user);
        return UserDTO.fromEntity(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.getUserByName(username);
        userRepository.deleteById(user.getId());
    }

    @Override
    public void giveAdmin(String username) {
        User user = userRepository.getUserByName(username);
        user.addRole(roleRepository.getByName(RoleName.ADMIN.name()).orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        userRepository.save(user);
    }

    @Override
    public MessageResponse removeAdmin(String username) {
        User user = userRepository.getUserByName(username);
        user.removeRole(roleRepository.getByName(RoleName.ADMIN.name()).orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        userRepository.save(user);
        return new MessageResponse(Boolean.TRUE, "You took ADMIN role from user: " + username);
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
        userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public void verifyEmailActivation(String uniqueCode) {
        ResetPassword resetPassword = resetPasswordRepository.findByUniqueCode(uniqueCode).orElseThrow(() -> new ResetPasswordInvalidException("Unique Code Tidak Terdaftar"));
        User user = resetPassword.getUser();
        user.setUserActive(true);
        userRepository.save(user);
    }

    @Override
    public UserDTO verifyResetPasswordLink(String uniqueCode) {
        ResetPassword resetPassword = resetPasswordRepository.findByUniqueCode(uniqueCode).orElseThrow(() -> new ResetPasswordInvalidException("Invalid code " + uniqueCode));

        if (LocalDateTime.now().isAfter(resetPassword.getExpired())) {
            throw new ResetPasswordInvalidException("Unique code " + uniqueCode + " expired");
        }
        User user = resetPassword.getUser();
        resetPasswordRepository.deleteByUser(user);
        return UserDTO.fromEntity(user);
    }

    @Override
    public void setNewPassword(User user, String password) {
        UserPassword userPassword = userPasswordRepository.findByUser(user);
        if (userPassword == null) {
            userPassword = new UserPassword();
            userPassword.setUser(user);
        }
        userPassword.setPassword(passwordEncoder.encode(password));
        userPasswordRepository.save(userPassword);
    }

    @Override
    public void forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            resetPasswordRepository.deleteByUser(optionalUser.get());
            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setUser(optionalUser.get());
            resetPasswordRepository.save(resetPassword);
        }
    }
}
