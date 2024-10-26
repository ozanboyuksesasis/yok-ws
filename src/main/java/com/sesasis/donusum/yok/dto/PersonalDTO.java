package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO {

    private Long id;
    private Long idariBirimId;
    private Long gorevId;

    @NotBlank(message = "Unvan boş olamaz")
    private String unvan;

    @NotBlank(message = "İsim boş olamaz")
    private String isim;

    @NotBlank(message = "Kimlik numarası boş olamaz")
    private String kimlikNumarasi;

    @NotBlank(message = "Soyisim boş olamaz")
    private String soyisim;

    private String email;
    private String fotografUrl;
    private String profilUrl;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate girisTarihi;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate cikisTarihi;

    private boolean aktif;

    private List<GorevDonemiDTO> gorevDonemleri;
}
