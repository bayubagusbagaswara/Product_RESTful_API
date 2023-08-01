package com.product.restful.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "reset_password")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "generated")
//    private LocalDate generated = LocalDate.now();
//
//    @Column(name = "expired")
//    private LocalDate expired = LocalDate.now().plus(1, ChronoUnit.DAYS);

    @ManyToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_reset_password_user_id"), referencedColumnName = "id")
    private User user;

    @Column(name = "unique_code", nullable = false)
    private String uniqueCode = UUID.randomUUID().toString();

}
