package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
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
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public WebResponse<UserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest newUser,
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        final UserResponse userResponse = userService.updateUser(username, newUser, currentUser);
        return WebResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(userResponse)
                .build();
    }

    @PutMapping("/{username}/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addRoleToUser(
            @PathVariable(value = "username") String username,
            @RequestBody RoleName roleName) {
        userService.addRoleToUser(username, roleName);
        return WebResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data("Success added role to user")
                .build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public WebResponse<ApiResponse> deleteUser(
            @PathVariable(value = "username") String username,
            @CurrentUser UserPrincipal currentUser) {

        ApiResponse apiResponse = userService.deleteUser(username, currentUser);
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

    @PutMapping("/{username}/removeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<ApiResponse> removeAdmin(@PathVariable(value = "username") String username) {
        ApiResponse apiResponse = userService.removeAdmin(username);
        return WebResponse.<ApiResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(apiResponse)
                .build();
    }

}
