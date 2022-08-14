package com.product.restful.dto.refreshToken;

import com.product.restful.dto.user.UserDTO;
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
    private UserDTO user;
    private String refreshToken;
    private Instant expiryDate;

    public static RefreshTokenResponse mapFromEntity(RefreshToken refreshToken) {
        return new RefreshTokenResponse(refreshToken.getId(), UserDTO.fromEntity(refreshToken.getUser()), refreshToken.getRefreshToken(), refreshToken.getExpiryDate());
    }

}
