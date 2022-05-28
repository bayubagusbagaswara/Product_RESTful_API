package com.product.restful.dto.user;

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
public class UpdateUserRequest {

    @NotBlank(message = "Firstname must not be blank")
    @Size(max = 40, message = "Firstname maximum length 40 characters")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    @Size(max = 40, message = "Lastname maximum length 40 characters")
    private String lastName;

    @NotBlank(message = "Username must not be blank")
    @Size(max = 15, message = "Username maximum length 15 characters")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 100, message = "Password minimum length 6 characters and maximum 100 characters")
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be formatted")
    private String email;
}
