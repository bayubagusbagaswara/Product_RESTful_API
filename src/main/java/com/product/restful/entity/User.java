package com.product.restful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.time.Instant;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status_record", nullable = false)
    private StatusRecord statusRecord = StatusRecord.ACTIVE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_role_id_user"), referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_user_role_id_role"), referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String email, String username, String password, Instant createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.setCreatedAt(createdAt);
    }

    @Override
    public Instant getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public Instant getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public void setCreatedAt(Instant createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    public void setUpdatedAt(Instant updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    public void removeRoles() {
        for (Role role : new HashSet<>(roles)) {
            removeRole(role);
        }
    }

}
