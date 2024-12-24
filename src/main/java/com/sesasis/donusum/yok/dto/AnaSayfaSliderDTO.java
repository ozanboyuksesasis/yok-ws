package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Base64;

@Data
public class AnaSayfaSliderDTO extends BaseDTO<AnaSayfaSlider> {
    private Long id;
    private Long siraNo;

    @NotNull(message = "Aktif durumu boş olamaz.")
    private Boolean aktifMi;

    @NotNull(message = "Dil kategorisi boş olamaz.")
    private Long genelDilCategoryId;

    @NotEmpty(message = "Başlık alanı boş olamaz.")
    private String baslik;

    @NotEmpty(message = "Alt başlık alanı boş olamaz.")
    private String altBaslik;


    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdAt;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate updateAt;

    private String path;
    private String base64content;

    private String contentDetay;

    public AnaSayfaSlider toEntity() {
        AnaSayfaSlider anaSayfaSlider = new AnaSayfaSlider();
        anaSayfaSlider.setId(id);
        anaSayfaSlider.setSiraNo(siraNo);
        anaSayfaSlider.setAktifMi(aktifMi);
        anaSayfaSlider.setBaslik(baslik);
        anaSayfaSlider.setAltBaslik(altBaslik);
        anaSayfaSlider.setCreatedAt(createdAt);
        anaSayfaSlider.setUpdateAt(updateAt);
        anaSayfaSlider.setPath(path);


        if (contentDetay != null) {
            anaSayfaSlider.setContentDetay(Base64.getDecoder().decode(contentDetay));
        }
        return anaSayfaSlider;
    }
    public void setContentDetay(byte[] contentDetay) {
        this.contentDetay = Base64.getEncoder().encodeToString(contentDetay);
    }
}