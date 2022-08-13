package com.product.restful.service.impl;

import com.product.restful.entity.user.User;
import com.product.restful.entity.user.UserPassword;
import com.product.restful.entity.user.UserPrincipal;
import com.product.restful.repository.UserPasswordRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.CustomUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository, UserPasswordRepository userPasswordRepository) {
        this.userRepository = userRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

    @Override
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

        return getUserPrincipal(user);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));

        return getUserPrincipal(user);
    }

    private UserPrincipal getUserPrincipal(User user) {
        UserPassword userPassword = userPasswordRepository.findByUserId(user.getId());

        // buat object simple granted authority
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return UserPrincipal.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(userPassword.getPassword())
                .authorities(authorities)
                .build();
    }
}
