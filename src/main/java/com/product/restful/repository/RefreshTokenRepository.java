package com.product.restful.repository;

import com.product.restful.entity.RefreshToken;
import com.product.restful.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String token);

    Optional<RefreshToken> findByUser(User user);

    void deleteByRefreshToken(String token);

    void deleteByUserId(Long userId);
}
