package com.sesasis.donusum.yok.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;


@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseModel<T extends BaseDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int deleted=0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm XXX")
    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm XXX")
    @UpdateTimestamp
    private OffsetDateTime updateDate;

    public abstract T toDTO();
}
