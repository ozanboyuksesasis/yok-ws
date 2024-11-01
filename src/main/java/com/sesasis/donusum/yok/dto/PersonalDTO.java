package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalDTO {

    private Long id;

    @NotNull(message = "İdari birim seçimi boş olamaz.")
    private Long idariBirimId;

    @NotNull(message = "Görev seçimi boş olamaz.")
    private Long gorevId;

    @NotBlank(message = "Unvan boş olamaz.")
    private String unvan;

    @NotBlank(message = "İsim boş olamaz.")
    private String isim;

    @NotBlank(message = "Kimlik numarası boş olamaz.")
    private String kimlikNumarasi;

    @NotBlank(message = "Soyisim boş olamaz.")
    private String soyisim;

    @Email(message = "Geçersiz e-posta formatı.")
    private String email;

    @NotBlank(message = "fotografUrl boş olamaz.")
    private String fotografUrl;

    @NotBlank(message = "profilUrl boş olamaz.")
    private String profilUrl;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate girisTarihi;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate cikisTarihi;

    private boolean aktif;

    private List<GorevDonemiDTO> gorevDonemleri;
}
