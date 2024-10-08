package com.sesasis.donusum.yok.dto;

import lombok.Data;

import java.util.List;

@Data
public class OnizleDTO {
	List<AnaSayfaSliderDTO> sliderList;
	List<AnaSayfaSolContentDTO> solContentList;
}
