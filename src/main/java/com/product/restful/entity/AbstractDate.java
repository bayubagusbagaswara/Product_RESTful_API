package com.product.restful.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"created_at", "updated_at", "deleted_at"},
        allowGetters = true
)
public abstract class AbstractDate implements Serializable {

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Paris")
//    private Date datetimewithzone;

    // konversi dari epoch millis menjadi localdate sesuai zone id
//    LocalDate date =
//            Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalDate();

//    LocalDateTime date =
//            LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), ZoneId.systemDefault());

    // create epoch saat ini
//        long epoch = Instant.now().toEpochMilli();
//        System.out.println(epoch);

    // convert dari epoch menjadi LocalDate (tanpa waktu)
//        LocalDate ld = Instant.ofEpochMilli(epoch)
//                .atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println(ld);

    // convert dari epoch menjadi LocalDateTime
//        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
//                .atZone(ZoneId.systemDefault()).toLocalDateTime();
//        System.out.println(ldt);

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_date;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false)
    @UpdateTimestamp
    private Date updated_date;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date", nullable = true)
    private Date deleted_date;
}


