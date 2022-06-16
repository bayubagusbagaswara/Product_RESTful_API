package com.product.restful.repository;

import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("FROM RefreshToken r WHERE r.refreshToken = :token")
    Optional<RefreshToken> getByRefreshToken(@RequestParam(name = "token") String token);

    @Query("FROM RefreshToken r WHERE r.user = :user")
    Optional<RefreshToken> getByUser(@RequestParam(name = "user") User user);

//    @Modifying
//    @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
    void deleteByUserId(Long userId);
}
