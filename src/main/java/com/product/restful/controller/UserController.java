package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.security.CurrentUser;
import com.product.restful.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<WebResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest user) {
        final UserResponse userResponse = userService.createUser(user);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User was created successfully", userResponse), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{username}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<WebResponse<UserResponse>> updateUser(
            @Valid @RequestBody UpdateUserRequest newUser,
            @PathVariable(name = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        final UserResponse userResponse = userService.updateUser(username, newUser, currentUser);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User updated successfully", userResponse), HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/addRole")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> addRoleToUser(
            @PathVariable(name = "username") String username,
            @RequestBody RoleRequest roleRequest) {

        userService.addRoleToUser(username, RoleName.valueOf(roleRequest.getName().toUpperCase()));
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Successfully added role to user: " + username), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable(name = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        ApiResponse apiResponse = userService.deleteUser(username, currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/giveAdmin")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
        ApiResponse apiResponse = userService.giveAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/removeAdmin")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> removeAdmin(@PathVariable(name = "username") String username) {
        ApiResponse apiResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
