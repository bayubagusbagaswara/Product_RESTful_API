package com.product.restful.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username or Email must not be blank")
    private String usernameOrEmail;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
