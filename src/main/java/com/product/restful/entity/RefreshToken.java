package com.product.restful.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "refresh_tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token", name = "refresh_tokens_token_unique")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_refresh_token_user_id"), referencedColumnName = "id")
    private User user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
