package com.product.restful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.product.restful.entity.audit.DateAudit;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.entity.enumerator.StatusRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "role_name_unique"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE roles SET status_record = 'INACTIVE' WHERE id = ?")
@Where(clause = "status_record = 'ACTIVE'")
public class Role extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, nullable = false)
    private RoleName name;


    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status_record", nullable = false)
    private StatusRecord statusRecord = StatusRecord.ACTIVE;

    public Role(RoleName name) {
        this.name = name;
    }

    @JsonIgnore
    @Override
    public Instant getCreatedAt() {
        return super.getCreatedAt();
    }

    @JsonIgnore
    @Override
    public Instant getUpdatedAt() {
        return super.getUpdatedAt();
    }
}
