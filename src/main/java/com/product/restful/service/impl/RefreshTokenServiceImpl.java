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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
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

        if (refreshTokenRepository.getByUser(user).isPresent()) {
            refreshTokenRepository.deleteByUserId(userId);
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setCreatedAt(Instant.now());

        refreshTokenRepository.save(refreshToken);
        return RefreshTokenResponse.mapToDto(refreshToken);
    }

    @Override
    public RefreshTokenResponse validateRefreshToken(String refreshToken) {
        RefreshToken token = verifyExpirationRefreshToken(refreshToken);
        return RefreshTokenResponse.mapToDto(token);
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.getByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));
    }

    @Override
    public RefreshToken verifyExpirationRefreshToken(String refreshToken) {
        RefreshToken token = getRefreshToken(refreshToken);

        // jika expiryDate token < tanggal sekarang, maka hapus refreshToken, karena sudah kadaluarsa
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken(), "Refresh token was expired. Please make a new login request");
        }
        return token;
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.delete(verifyExpirationRefreshToken(token));
    }

}
