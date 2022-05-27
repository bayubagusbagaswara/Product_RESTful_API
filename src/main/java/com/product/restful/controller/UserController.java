package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.security.CurrentUser;
import com.product.restful.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
        final UserResponse userResponse = userService.createUser(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest newUser,
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        final UserResponse userResponse = userService.updateUser(username, newUser, currentUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{username}/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@PathVariable(value = "username") String username, String roleName) {
        userService.addRoleToUser(username, roleName);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        ApiResponse apiResponse = userService.deleteUser(username, currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/removeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> removeAdmin(@PathVariable(value = "username") String username) {
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
