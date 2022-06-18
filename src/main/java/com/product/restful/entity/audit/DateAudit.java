package com.product.restful.entity.audit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = { "createdAt", "updatedAt" },
        allowGetters = true
)
public abstract class DateAudit {

    @CreatedDate
    @JsonFormat(timezone = "Asia/Jakarta")
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @JsonFormat(timezone = "Asia/Jakarta")
    @Column(nullable = false)
    private Instant updatedAt;
}
