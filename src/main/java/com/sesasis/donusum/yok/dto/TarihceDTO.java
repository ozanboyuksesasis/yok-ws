package com.sesasis.donusum.yok.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TarihceDTO{

    private int id;

    @NotBlank(message = "Hata : Tarih boş olamaz.")
    private String tarihce;

}
