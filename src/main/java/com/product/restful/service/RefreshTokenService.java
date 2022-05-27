package com.product.restful.service;

import com.product.restful.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByToken(String token);

    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
