package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HaberDTO {

    private Long id;

    private Long siraNo;

    private Long newDomainId;

    @NotNull(message = "Dil kategorisi boş olamaz.")
    private Long haberDilId;

    @NotEmpty(message = "Başlık alanı boş olamaz.")
    @Size(max = 100, message = "Başlık en fazla 100 karakter olabilir.")
    private String baslık;

    @Size(max = 500, message = "Özet en fazla 500 karakter olabilir.")
    private String ozet;

    @Lob
    private String detay;

    @URL(message = "Geçerli bir URL giriniz.")
    private String sayfaUrl;;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdAt ;
}
