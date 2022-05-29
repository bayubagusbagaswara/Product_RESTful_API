package com.product.restful.service.impl;

import com.product.restful.dto.refreshToken.RefreshTokenResponse;
import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;
import com.product.restful.exception.RefreshTokenNotFoundException;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.exception.TokenRefreshException;
import com.product.restful.repository.RefreshTokenRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.refreshTokenExpired}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshTokenResponse generateRefreshToken(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Instant dateNow = Instant.now();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(dateNow);
        refreshToken.setExpiryDate(dateNow.plusMillis(refreshTokenDurationMs));
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return RefreshTokenResponse.mapToDto(refreshToken);
    }

    @Override
    public RefreshTokenResponse validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getRefreshToken(), "Refresh token was expired. Please make a new login request");
        }

        return RefreshTokenResponse.mapToDto(refreshToken);
    }

    @Override
    public RefreshTokenResponse getToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));

        return RefreshTokenResponse.mapToDto(refreshToken);
    }

    @Override
    public RefreshTokenResponse verifyExpiration(RefreshToken refreshToken) {
        // jika token expired < tanggal sekarang, maka hapus token nya
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getRefreshToken(), "Refresh token was expired. Please make a new login request");
        }
        return RefreshTokenResponse.mapToDto(refreshToken);
    }

    @Override
    public void deleteRefreshTokenByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }

}
