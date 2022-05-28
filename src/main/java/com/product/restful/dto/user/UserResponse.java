package com.product.restful.dto.user;

import com.product.restful.entity.Role;
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

    private String id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private Set<Role> roles = new HashSet<>();
}
