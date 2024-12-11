package com.sesasis.donusum.yok.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnaBaslikDTO {


    @NotBlank(message = "Hata: Bu alan boşluklardan oluşamaz.")
    @Size(min = 2, max = 70, message = "Hata: Başlık 2 ile 70 karakter arasında olmalı.")
    @Pattern(regexp = "^[^0-9]+$", message = "Hata: Başlık rakam içeremez.")
    private String baslik;


}