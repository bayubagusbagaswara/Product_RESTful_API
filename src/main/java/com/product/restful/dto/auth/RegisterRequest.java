package com.product.restful.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 4, max = 40, message = "Firstname minimum length of 4 characters and a maximum of 40 characters")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    @Size(min = 4, max = 40, message = "Lastname minimum length of 4 characters and a maximum of 40 characters")
    private String lastName;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 15, message = "Username minimum length of 3 characters and a maximum of 15 characters")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must match the format")
    @Size(max = 40, message = "Email maximum length of 40 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password minimum length of 6 characters and a maximum of 20 characters")
    private String password;
}
