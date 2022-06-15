package com.product.restful.dto.refreshToken;

import com.product.restful.dto.user.UserDto;
import com.product.restful.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

    private Long id;

    private UserDto user;

    private String refreshToken;

    private Instant expiryDate;

    public static RefreshTokenResponse mapToDto(RefreshToken refreshToken) {
        return new RefreshTokenResponse(refreshToken.getId(), UserDto.fromUser(refreshToken.getUser()), refreshToken.getRefreshToken(), refreshToken.getExpiryDate());
    }

}
