package com.product.restful.service.impl;

import com.product.restful.entity.RefreshToken;
import com.product.restful.repository.RefreshTokenRepository;
import com.product.restful.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken findByRefreshToken(String token) {
        return null;
    }

    @Override
    public RefreshToken generateRefreshToken() {
        return null;
    }

    @Override
    public void validateRefreshToken(String token) {

    }

    @Override
    public void deleteRefreshToken(String token) {

    }
}
