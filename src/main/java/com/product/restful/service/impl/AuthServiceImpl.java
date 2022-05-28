package com.product.restful.service.impl;

import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.refreshToken.RefreshTokenResponse;
import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.exception.AppException;
import com.product.restful.exception.BlogApiException;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.security.JwtTokenProvider;
import com.product.restful.service.AuthService;
import com.product.restful.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserResponse signUp(SignUpRequest signUpRequest) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(Instant.now());

        Role roleAdmin = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET));

        Role roleUser = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET));

        Set<Role> roleSet = new HashSet<>();

        if (userRepository.count() == 0) {
            roleSet.add(roleAdmin);
            roleSet.add(roleUser);
        }
        roleSet.add(roleUser);
        user.setRoles(roleSet);

        userRepository.save(user);
        return UserResponse.mapToDto(user);
    }

    @Override
    public AuthenticationResponse signIn(LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        final String token = jwtTokenProvider.generateToken(authentication);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken(userPrincipal.getId()).getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsernameOrEmail())
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        RefreshTokenResponse refreshTokenResponse = refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtTokenProvider.generateTokenFromUsername(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenResponse.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.validateRefreshToken(logoutRequest.getRefreshToken());

        refreshTokenService.deleteRefreshTokenByUserId(refreshTokenResponse.getUser().getId());
    }

}
