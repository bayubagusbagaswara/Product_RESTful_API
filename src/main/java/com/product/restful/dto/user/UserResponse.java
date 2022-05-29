package com.product.restful.dto.user;

import com.product.restful.entity.Role;
import com.product.restful.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private Set<Role> roles = new HashSet<>();

    public static UserResponse mapToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public static User mapToEntity(UserResponse userResponse) {
        return User.builder()
                .id(userResponse.getId())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .username(userResponse.getUsername())
                .password(userResponse.getPassword())
                .email(userResponse.getEmail())
                .roles(userResponse.getRoles())
                .build();
    }
}
