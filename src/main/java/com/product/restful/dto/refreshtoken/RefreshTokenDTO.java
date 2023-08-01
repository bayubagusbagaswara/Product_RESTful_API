package com.product.restful.dto.refreshtoken;

import com.product.restful.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {

    private Long id;
    private UserDTO user;
    private String refreshToken;
    private Instant expiryDate;
}
