package com.product.restful.dto.refreshtoken;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh Token must not be blank")
    private String refreshToken;
}
