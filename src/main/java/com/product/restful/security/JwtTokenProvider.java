package com.product.restful.security;

import com.product.restful.entity.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtTokenProvider berguna sebagai pembuat token
 */
@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value(value = "${app.jwtSecret}")
    private String jwtSecret;

    @Value(value = "${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        // balikan data ini adalah UserPrincipal (implement dari UserDetails)
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // set tanggal sekarang
        Date now = new Date();

        // set tanggal untuk masa expired token nya, rumus nya adalah tanggal sekarang + jangka waktu yang ditentukan
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // balikan token
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // method untuk mengambil id user dari token jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

    // method untuk validasi token, apakah benar token tersebut adalah token yang digenerate oleh aplikasi kita
    public boolean validateToken(String authToken) {
        try {
            // cocokkan authToken dengan jwtSecret
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty");
        }
        return false;
    }
}
