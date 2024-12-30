package com.sesasis.donusum.yok.dto;
import com.sesasis.donusum.yok.enums.DosyaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DosyaDTO {

    private Long id;

    private String url;
    @NotBlank(message = "Hata : Başlık boş bırakılamaz.")
    private String baslik;

    @NotBlank(message = "Hata : Sıra numarası  boş bırakılamaz.")
    private Long siraNo;

    @NotBlank(message = "Hata : Aktif durumu boş bırakılamaz.")
    private boolean aktifMi ;

    private Long galeriId;

    private Long domainId;

    private DosyaType dosyaType; // Enum alanı ön yüzden STORIE ya da HIZLIBAGLANTI şeklinde gelmesi.

}
