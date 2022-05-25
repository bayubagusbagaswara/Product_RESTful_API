package com.product.restful.service.impl;

import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.jwt.JwtAuthenticationResponse;
import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import com.product.restful.entity.User;
import com.product.restful.exception.AppException;
import com.product.restful.exception.BlogApiException;
import com.product.restful.repository.RoleRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.security.JwtTokenProvider;
import com.product.restful.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User signUp(SignUpRequest signUpRequest) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName().toLowerCase())
                .lastName(signUpRequest.getLastName().toLowerCase())
                .username(signUpRequest.getUsername().toLowerCase())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        // ini coba di test, apakah saat pertama kali signup User mendapatkan role apa?
        // harusnya cuma role MEMBER
        // Atau mungkin user yang pertama kali signup akan mendapatkan role ADMIN dan MEMBER
        // user yang daftar selanjutnya hanya akan mendapatkan role MEMBER
        if (userRepository.count() == 0) {

            roles.add(roleRepository.findByName(RoleName.MEMBER.getRoleName())
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

            roles.add(roleRepository.findByName(RoleName.ADMIN.getRoleName())
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        }

        roles.add(roleRepository.findByName(RoleName.MEMBER.getRoleName())
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signIn(LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtTokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }
}
