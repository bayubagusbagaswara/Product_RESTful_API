package com.product.restful.entity;

import com.product.restful.entity.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serial;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "users_username_unique"),
        @UniqueConstraint(columnNames = "email", name = "users_email_unique")
})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET status_record = 'INACTIVE' WHERE id = ?")
@Where(clause = "status_record = 'ACTIVE'")
public class User extends DateAudit {

//    @Serial
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

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
            joinColumns = @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_role_id_user"), referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_user_role_id_role"), referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

//    public Set<Role> getRoles() {
//        if (roles == null) {
//            return null;
//        }
//        return new HashSet<>(roles);
//    }
//
//    public void setRoles(Set<Role> roles) {
//        if (roles == null) {
//            this.roles = null;
//        } else {
//            this.roles = Collections.unmodifiableSet(roles);
//        }
//    }
//
//    public void addRole(Role role) {
//        this.roles.add(role);
//        role.getUsers().add(this);
//    }
//
//    public void removeRole(Role role) {
//        this.getRoles().remove(role);
//        role.getUsers().remove(this);
//    }
//
//    public void removeRoles() {
//        for (Role role : new HashSet<>(roles)) {
//            removeRole(role);
//        }
//    }

}
