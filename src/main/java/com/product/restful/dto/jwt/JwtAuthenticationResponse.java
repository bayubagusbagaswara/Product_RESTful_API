package com.product.restful.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private Instant expiresAt;

    private String tokenType = "Bearer";

    private String username;
}
