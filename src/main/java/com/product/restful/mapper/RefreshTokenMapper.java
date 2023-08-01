package com.product.restful.mapper;

import com.product.restful.dto.refreshtoken.RefreshTokenDTO;
import com.product.restful.entity.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    private final UserMapper userMapper;

    public RefreshTokenMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RefreshTokenDTO mapFromRefreshToken(RefreshToken refreshToken) {
        return RefreshTokenDTO.builder()
                .id(refreshToken.getId())
                .user(userMapper.mapFromUser(refreshToken.getUser()))
                .refreshToken(refreshToken.getToken())
                .expiryDate(refreshToken.getExpiryDate())
                .build();
    }
}
