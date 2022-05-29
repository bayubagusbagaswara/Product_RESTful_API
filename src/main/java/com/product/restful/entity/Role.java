package com.product.restful.entity;

import com.product.restful.entity.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "roles_name_unique")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE roles SET status_record = 'INACTIVE' WHERE id = ?")
@Where(clause = "status_record = 'ACTIVE'")
public class Role extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, nullable = false)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status_record", nullable = false)
    private StatusRecord statusRecord = StatusRecord.ACTIVE;
}
