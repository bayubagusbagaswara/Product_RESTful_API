package com.product.restful.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_password")
@Data
@NoArgsConstructor
public class UserPassword {

    @Id
    @Column(name = "id_user")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_password_id_user"), referencedColumnName = "id")
    private User user;

    @Column(name = "password")
    private String password;

}
