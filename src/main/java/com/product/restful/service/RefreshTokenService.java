package com.product.restful.service;

import com.product.restful.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByRefreshToken(String token);

    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
