package com.product.restful.service.impl;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RefreshTokenServiceImplTest {

//    private final static Logger log = LoggerFactory.getLogger(RefreshTokenServiceImplTest.class);
//
//    @Autowired
//    RefreshTokenService refreshTokenService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void generateRefreshToken() {
//        Long userId = 1L;
//        RefreshTokenDTO refreshTokenDTO = refreshTokenService.generateRefreshToken(userId);
//        log.info("Token: {}", refreshTokenDTO.getRefreshToken());
//    }
//
//    @Test
//    void validateRefreshToken() {
//        String refreshToken = "";
//        RefreshTokenDTO refreshTokenDTO = refreshTokenService.validateRefreshToken(refreshToken);
//        assertEquals(refreshToken, refreshTokenDTO.getRefreshToken());
//    }
//
//    @Test
//    void getRefreshToken() {
//        String refreshToken = "";
//        RefreshToken token = refreshTokenService.getRefreshToken(refreshToken);
//        assertEquals(refreshToken, token.getRefreshToken());
//    }
//
//    @Test
//    void verifyExpirationRefreshToken() {
//        String refreshToken = "";
//        RefreshToken token = refreshTokenService.verifyExpirationRefreshToken(refreshToken);
//        assertNotNull(token);
//        assertEquals(refreshToken, token.getRefreshToken());
//    }
//
//    @Test
//    void deleteRefreshToken() {
//        String refreshToken = "";
//        refreshTokenService.deleteRefreshToken(refreshToken);
//        assertThrows(RefreshTokenNotFoundException.class, () -> {
//            RefreshToken token = refreshTokenService.getRefreshToken(refreshToken);
//        });
//    }
}