package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMenuDTO {
    private Long id ;

    @NotNull(message = "Domain id giriniz.")
    private Long domainId;
    private Long fotografId;

    private int parentId ;

    private String label ;

    @NotBlank(message = "Hata : İsim boş bırakılamaz.")
    private String isim;

    @NotNull
    @NotBlank(message = "Hata : Url boş bırakılamaz.")
    private  String menuUrl;

    private boolean anaSayfaMi;


}
