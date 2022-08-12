package com.product.restful.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_passwords")
@Data
@NoArgsConstructor
public class UserPassword {

    @Id
    @Column(name = "id_user")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_users_passwords_user_id"))
    private User user;

    @Column(name = "password")
    private String password;
}
