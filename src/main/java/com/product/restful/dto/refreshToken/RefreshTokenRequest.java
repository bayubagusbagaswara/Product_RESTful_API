package com.product.restful.dto.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Username must not be blank and not email")
    private String username;

    @NotBlank(message = "Refresh Token must not be blank")
    private String refreshToken;

}
