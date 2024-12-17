package com.sesasis.donusum.yok.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnaDomainSliderDTO {

    private Long id;
    private String baslik;
    private String icerik;
    private int siraNo;
    private String foto; // Base64 encoded string
    private List<GenelDilCategoryDTO> diller;
}