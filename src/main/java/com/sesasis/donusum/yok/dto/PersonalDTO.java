package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO {

    private Long id;


    @NotBlank(message = "Unvan boş olamaz")
    private String unvan;

    @NotBlank(message = "İsim boş olamaz")
    private String isim;

    @NotBlank(message = "Soyisim boş olamaz")
    private String soyisim;

    private String email;

    private String fotografUrl;

    private String profilUrl;


    private boolean aktif;
}
