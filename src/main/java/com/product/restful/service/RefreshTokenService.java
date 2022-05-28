package com.product.restful.service;

import com.product.restful.dto.refreshToken.RefreshTokenResponse;

public interface RefreshTokenService {

    RefreshTokenResponse generateRefreshToken(Long userId);

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
