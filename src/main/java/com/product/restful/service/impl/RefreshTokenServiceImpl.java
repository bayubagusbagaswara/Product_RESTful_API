package com.product.restful.service.impl;

import com.product.restful.entity.RefreshToken;
import com.product.restful.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Override
    public RefreshToken findByToken(String token) {
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
