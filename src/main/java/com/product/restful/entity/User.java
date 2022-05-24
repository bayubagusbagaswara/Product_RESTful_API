package com.product.restful.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "users_username_unique"),
        @UniqueConstraint(columnNames = "email", name = "users_email_unique")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "username", length = 15, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(name = "email", length = 40)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_user_role_id_user"),
            inverseForeignKey = @ForeignKey(name = "fk_user_role_id_role")
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        if (roles == null) {
            return null;
        }
        return new HashSet<>(roles);
    }

    public void setRoles(Set<Role> roles) {
        if (roles == null) {
            this.roles = null;
        } else {
            this.roles = Collections.unmodifiableSet(roles);
        }
    }

//    public void addRole(Role role) {
//        roles.add(role);
//        role.getUserList().add(this);
//    }
//
//    public void addRoles(Set<Role> roles) {
//        roles.forEach(this::addRole);
//    }
//
//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.getUserList().remove(this);
//    }

}
