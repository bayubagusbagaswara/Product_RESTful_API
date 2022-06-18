package com.product.restful.repository;

import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;
import com.product.restful.exception.ResourceNotFoundException;
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

    Optional<RefreshToken> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
