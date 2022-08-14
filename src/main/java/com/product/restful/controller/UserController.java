package com.product.restful.controller;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.CreateRoleRequest;
import com.product.restful.dto.user.CreateUserRequest;
import com.product.restful.dto.user.UpdateUserRequest;
import com.product.restful.dto.user.UserDTO;
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
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
        final UserDTO userDto = userService.createUser(user);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, String.format("User %s was created successfully", userDto.getUsername()), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{username}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<WebResponse<UserDTO>> updateUser(@Valid @RequestBody UpdateUserRequest newUser, @PathVariable(name = "username") String username) {
        final UserDTO userDto = userService.updateUser(username, newUser);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User updated successfully", userDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/addRole")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> addRoleToUser(@PathVariable(name = "username") String username, @RequestBody CreateRoleRequest createRoleRequest) {
        userService.addRoleToUser(username, createRoleRequest.getName().toUpperCase());
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "Successfully added role " + createRoleRequest.getName().toUpperCase() + " to user: " + username), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable(name = "username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "You successfully deleted profile of: " + username), HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/giveAdmin")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> giveAdmin(@PathVariable(name = "username") String username) {
        userService.giveAdmin(username);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "You gave ADMIN role to user: " + username), HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/removeAdmin")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> removeAdmin(@PathVariable(name = "username") String username) {
        MessageResponse messageResponse = userService.removeAdmin(username);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

}
