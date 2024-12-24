package com.sesasis.donusum.yok.dto;
import com.sesasis.donusum.yok.entity.DosyaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DosyaDTO {

    private Long id;

    private String url;
    @NotBlank(message = "Hata : Başlık boş bırakılamaz.")
    private String baslik;

    @NotBlank(message = "Hata : Alt başlık boş bırakılamaz.")
    private String altBaslik;

    @NotBlank(message = "Hata : Sıra numarası  boş bırakılamaz.")
    private Long siraNo;

    @NotBlank(message = "Hata : Aktif durumu boş bırakılamaz.")
    private boolean aktifMi ;

    @NotBlank(message = "Hata : İçerik  boş bırakılamaz.")
    private String contentDetay;

    String base64content;

    private Long galeriId;

    private Long domainId;

    private DosyaType dosyaType; // Enum alanı

}
