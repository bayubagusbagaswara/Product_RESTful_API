package com.product.restful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.product.restful.entity.audit.UserDateAudit;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "roles_name_unique")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE roles SET status_record = 'INACTIVE' WHERE id = ?")
@Where(clause = "status_record = 'ACTIVE'")
public class Role extends UserDateAudit {

//    @Serial
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, nullable = false)
    private RoleName name;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private Set<User> users = new HashSet<>();

    public Role(RoleName name) {
        this.name = name;
    }

//    public void addUser(User user) {
//        this.users.add(user);
//        user.getRoles().add(this);
//    }
//
//    public void removeUser(User user) {
//        this.getUsers().remove(user);
//        user.getRoles().remove(this);
//    }
//
//    public void removeUsers() {
//        for (User user : new HashSet<>(users)) {
//            removeUser(user);
//        }
//    }
}
