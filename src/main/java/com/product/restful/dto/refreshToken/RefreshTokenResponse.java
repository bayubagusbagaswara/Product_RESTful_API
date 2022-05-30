package com.product.restful.dto.refreshToken;

import com.product.restful.dto.user.UserResponse;
import com.product.restful.entity.RefreshToken;
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

    public static RefreshTokenResponse mapToDto(RefreshToken refreshToken) {
        return RefreshTokenResponse.builder()
                .id(refreshToken.getId())
                .user(UserResponse.fromUser(refreshToken.getUser()))
                .refreshToken(refreshToken.getRefreshToken())
                .expiryDate(refreshToken.getExpiryDate())
                .build();
    }

    public static RefreshToken mapToEntity(RefreshTokenResponse refreshTokenResponse) {
        return RefreshToken.builder()
                .id(refreshTokenResponse.getId())
                .user(UserResponse.fromUserResponse(refreshTokenResponse.getUser()))
                .refreshToken(refreshTokenResponse.getRefreshToken())
                .expiryDate(refreshTokenResponse.getExpiryDate())
                .build();
    }
}
