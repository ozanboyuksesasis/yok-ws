package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderDTO {

    private Long id;

    private Long siraNo;

    private String baslik;

    private String slogan;

    private String aciklama;

    private String sayfaUrl;

    private Long domainId;

    private List<FotografDTO> fotografDTOS;

}
