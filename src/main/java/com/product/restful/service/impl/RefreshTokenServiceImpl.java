package com.product.restful.service.impl;

import com.product.restful.dto.refreshtoken.RefreshTokenDTO;
import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.user.User;
import com.product.restful.exception.RefreshTokenNotFoundException;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.exception.TokenRefreshException;
import com.product.restful.mapper.RefreshTokenMapper;
import com.product.restful.repository.RefreshTokenRepository;
import com.product.restful.repository.UserRepository;
import com.product.restful.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenMapper refreshTokenMapper;

//    @Override
//    public RefreshTokenDTO generateRefreshToken(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//
//        if (refreshTokenRepository.findByUserId(userId).isPresent()) {
//            refreshTokenRepository.delete(user.getRefreshToken());
//        }
//
//        RefreshToken refreshToken = new RefreshToken(
//                user, UUID.randomUUID().toString().replace("-",""),
//                Instant.now().plusMillis(refreshTokenDurationMs), Instant.now()
//        );
//
//        refreshTokenRepository.save(refreshToken);
//        return refreshTokenMapper.mapFromRefreshToken(refreshToken);
//    }

    @Override
    public RefreshTokenDTO validateRefreshToken(String refreshToken) {
        RefreshToken token = verifyExpirationRefreshToken(refreshToken);
        return refreshTokenMapper.mapFromRefreshToken(token);
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.getByToken(refreshToken).orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));
    }

    @Override
    public RefreshToken verifyExpirationRefreshToken(String refreshToken) {
        RefreshToken token = getRefreshToken(refreshToken);

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new login request");
        }
        return token;
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.delete(verifyExpirationRefreshToken(token));
    }

}
