package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Entity
@Data
public class AnaSayfaSlider extends BaseModel<AnaSayfaSliderDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sira_no")
    private Long siraNo;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @Column(name = "baslik")
    private String baslik;

    @Column(name = "alt_baslik")
    private String altBaslik;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "dil_category_id")
    private GenelDilCategory genelDilCategory;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] contentDetay;

    @Column(name = "path")
    private String path;

    @Override
    public AnaSayfaSliderDTO toDTO() {
        AnaSayfaSliderDTO anaSayfaSliderDTO = new AnaSayfaSliderDTO();
        anaSayfaSliderDTO.setId(getId());
        anaSayfaSliderDTO.setSiraNo(siraNo);
        anaSayfaSliderDTO.setAktifMi(aktifMi);
        anaSayfaSliderDTO.setBaslik(baslik);
        anaSayfaSliderDTO.setAltBaslik(altBaslik);
        anaSayfaSliderDTO.setCreatedAt(createdAt);
        anaSayfaSliderDTO.setUpdateAt(updateAt);
        if (genelDilCategory != null) {
            anaSayfaSliderDTO.setGenelDilCategoryId(genelDilCategory.getId());
        }
        if (contentDetay != null) {
            anaSayfaSliderDTO.setContentDetay(contentDetay);
        }
        anaSayfaSliderDTO.setPath(path);
        return anaSayfaSliderDTO;
    }
}