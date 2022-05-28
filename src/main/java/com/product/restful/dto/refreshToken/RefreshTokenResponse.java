package com.product.restful.dto.refreshToken;

import com.product.restful.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

    private Long id;

    private UserResponse user;

    private String refreshToken;

    private Instant expiryDate;
}
