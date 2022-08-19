package com.product.restful.controller;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.RegisterRequest;
import com.product.restful.dto.user.UserDTO;
import com.product.restful.entity.user.User;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.AuthService;
import com.product.restful.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserService userService, UserRepository userRepository) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        String userId = authService.register(registerRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(userId).toUri();
        return ResponseEntity
                .created(location)
                .body(new MessageResponse(Boolean.TRUE, "User registered successfully"));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<WebResponse<AuthenticationResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authService.login(loginRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User authenticated successfully", authenticationResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/create/refreshToken")
    public ResponseEntity<WebResponse<AuthenticationResponse>> createRefreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse authenticationResponse = authService.createRefreshToken(refreshTokenRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Refresh token successfully updated", authenticationResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<MessageResponse> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "Refresh token deleted successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/verify/email/activation")
    public ResponseEntity<MessageResponse> verifyEmailActivation(@RequestParam(name = "unique_code") String uniqueCode) {
        userService.verifyEmailActivation(uniqueCode);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "Email success activated"), HttpStatus.OK);
    }

    @GetMapping(value = "/verify/reset/password/link")
    public ResponseEntity<WebResponse<UserDTO>> verifyResetPasswordLink(@RequestParam(name = "unique_code") String uniqueCode) {
        UserDTO userDTO = userService.verifyResetPasswordLink(uniqueCode);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Success verify reset password link", userDTO), HttpStatus.OK);
    }

    // user akan melakukan setPassword nya
    // parameternya adalah id user, password, dan confirm password
    @PostMapping(value = "/{userId}/new/password")
    public ResponseEntity<MessageResponse> setNewPassword(@PathVariable(name = "userId") Long userId,
                                                          @RequestParam(name = "password") String password,
                                                          @RequestParam(name = "confirm_password") String confirmPassword) {
        // ambil data user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        // cek apakah password dan confirmPassword sama

        if (password.equals(confirmPassword)) {
            userService.setNewPassword(user, password);
        } else {
            // gagal set password
        }
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "Success set new password"), HttpStatus.OK);
    }

    // forgot password
    // cukup kita klik link forgotPassword, lalu akan dikirimkan link resetPassword ke email
    // lalu user akan klik link tersebut, kemudian dimunculkan form pengisian password dan confirm_password
}
