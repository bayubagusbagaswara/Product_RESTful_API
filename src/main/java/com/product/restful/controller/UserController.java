package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserDto;
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
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
        final UserDto userDto = userService.createUser(user);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, String.format("User %s was created successfully", userDto.getUsername()), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{username}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<WebResponse<UserDto>> updateUser(@Valid @RequestBody UpdateUserRequest newUser, @PathVariable(name = "username") String username) {
        final UserDto userDto = userService.updateUser(username, newUser);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User updated successfully", userDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/addRole")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> addRoleToUser(@PathVariable(name = "username") String username, @RequestBody RoleRequest roleRequest) {
        userService.addRoleToUser(username, roleRequest.getName().toUpperCase());
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Successfully added role " + roleRequest.getName().toUpperCase() + " to user: " + username), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "username") String username) {
        ApiResponse apiResponse = userService.deleteUser(username);
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
