package com.product.restful.service;

import com.product.restful.dto.refreshToken.RefreshTokenResponse;
import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;

public interface RefreshTokenService {

    RefreshTokenResponse generateRefreshToken(Long userId);

    RefreshTokenResponse validateRefreshToken(String token);

    RefreshTokenResponse getToken(String token);

    RefreshTokenResponse verifyExpiration(RefreshToken token);

    void deleteRefreshTokenByUser(User user);
}
