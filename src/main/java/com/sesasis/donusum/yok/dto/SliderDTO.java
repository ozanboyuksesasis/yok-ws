package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderDTO {

    private Long id;

    private Long siraNo;

    @NotEmpty(message = "Başlık boş olamaz.")
    @Size(max = 100, message = "Başlık 100 karakterden uzun olamaz.")
    private String baslik;

    @Size(max = 500, message = "Slogan 500 karakterden uzun olamaz.")
    private String slogan;

    @Size(max = 1000, message = "Açıklama 1000 karakterden uzun olamaz.")
    private String aciklama;

    @URL(message = "Geçerli bir URL olmalıdır.")
    private String sayfaUrl;

    private Long newDomainId;

    private List<FotografDTO> fotografDTOS;
}
