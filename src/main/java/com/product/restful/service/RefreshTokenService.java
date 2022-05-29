package com.product.restful.service;

import com.product.restful.dto.refreshToken.RefreshTokenResponse;
import com.product.restful.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshTokenResponse generateRefreshToken(Long userId);

    RefreshTokenResponse validateRefreshToken(String refreshToken);

    RefreshToken getRefreshToken(String refreshToken);

    RefreshToken verifyExpirationRefreshToken(String refreshToken);

    void deleteRefreshToken(String token);
}
