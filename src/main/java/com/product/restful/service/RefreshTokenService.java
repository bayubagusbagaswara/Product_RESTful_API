package com.product.restful.service;

import com.product.restful.dto.refreshtoken.RefreshTokenDTO;
import com.product.restful.entity.RefreshToken;

public interface RefreshTokenService {

//    RefreshTokenDTO generateRefreshToken(Long userId);

    RefreshTokenDTO validateRefreshToken(String refreshToken);

    RefreshToken getRefreshToken(String refreshToken);

    RefreshToken verifyExpirationRefreshToken(String refreshToken);

    void deleteRefreshToken(String token);
}
