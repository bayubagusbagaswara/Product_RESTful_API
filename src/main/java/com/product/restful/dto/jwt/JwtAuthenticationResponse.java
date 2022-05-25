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

    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

//    private String authenticationToken atau accessToken;
//    private String refreshToken;
//    private Instant expiresAt;
//    private String username;
}
