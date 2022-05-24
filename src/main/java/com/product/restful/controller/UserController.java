package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.User;
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
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest newUser,
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        final UserResponse userResponse = userService.updateUser(username, newUser, currentUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{username}/addRole")
    public ResponseEntity<String> addRole(@PathVariable(value = "username") String username, String roleName) {
        userService.addRoleToUser(username, roleName);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


//    @PutMapping("/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<User> updateUser(
//            @Valid @RequestBody User newUser,
//            @PathVariable(value = "username") String username,
//            @CurrentUser UserPrincipal currentUser) {
//
//        User updatedUSer = userService.updateUser(newUser, username, currentUser);
//        return new ResponseEntity<>(updatedUSer, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> deleteUser(
//            @PathVariable(value = "username") String username,
//            @CurrentUser UserPrincipal currentUser) {
//
//        ApiResponse apiResponse = userService.deleteUser(username, currentUser);
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("/{username}/giveAdmin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(value = "username") String username) {
//        ApiResponse apiResponse = userService.giveAdmin(username);
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("/{username}/removeAdmin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> removeAdmin(@PathVariable(value = "username") String username) {
//        ApiResponse apiResponse = userService.removeAdmin(username);
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("/setOrUpdateInfo")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserProfile> setAddress(
//            @CurrentUser UserPrincipal currentUser,
//            @Valid @RequestBody UserInfoRequest userInfoRequest) {
//
//        UserProfile userProfile = userService.setOrUpdateInfo(currentUser, userInfoRequest);
//        return new ResponseEntity<>(userProfile, HttpStatus.OK);
//    }
}
