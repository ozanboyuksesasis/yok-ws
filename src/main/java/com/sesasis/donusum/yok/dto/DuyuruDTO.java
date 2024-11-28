package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuyuruDTO {

    private Long id;

    private Long siraNo;


    @NotNull(message = "Aktif durumu boş olamaz.")
    private Boolean aktifMi ;

    @NotNull(message = "Dil kategorisi boş olamaz.")
    private Long genelDilCategoryId;

    @NotEmpty(message = "Başlık alanı boş olamaz.")
    private String baslik;

    @Size(max = 350, message = "Alt başlık en fazla üç yüz elli karakter olabilir.")
    private String altBaslik;


    @Lob
    @NotEmpty(message = "İçerik alanı boş olamaz.")
    @Size(min = 100,message = "İçerik minimum yüz karakter olmalıdır.")
    private String duyuruIcerik;

    @URL(message = "Geçerli bir URL giriniz.")
    private String sayfaUrl;;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdAt ;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate updateAt;



}
