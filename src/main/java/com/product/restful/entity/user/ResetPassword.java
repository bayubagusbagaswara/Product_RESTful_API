package com.product.restful.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reset_passwords", uniqueConstraints = {
        @UniqueConstraint(name = "reset_password_code_unique", columnNames = "unique_code")
})
@Data
@NoArgsConstructor
public class ResetPassword {

    private static final Integer RESET_PASSWORD_EXPIRY_DAYS = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime generated = LocalDateTime.now();

    private LocalDateTime expired = LocalDateTime.now().plusDays(RESET_PASSWORD_EXPIRY_DAYS);

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotBlank
    @Column(name = "unique_code", nullable = false)
    private String uniqueCode = UUID.randomUUID().toString();
}
