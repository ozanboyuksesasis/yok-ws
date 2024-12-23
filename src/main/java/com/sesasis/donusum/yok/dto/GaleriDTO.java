package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GaleriDTO {

    private Long id;

    @NotBlank(message = "Lütfen isim giriniz.")
    private String name;


}
