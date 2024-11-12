package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import lombok.Data;

@Data
public class AnaSayfaSliderDTO extends BaseDTO<AnaSayfaSlider> {
	String baslik;
	String slogan;
	int sira;
	String path;
	boolean aktif;
	MenuDTO menu;
	String base64content;
	String contentDetay;

	@Override
	public AnaSayfaSlider toEntity() {
		AnaSayfaSlider anaSayfaSlider = new AnaSayfaSlider();
		anaSayfaSlider.setId(getId());
		anaSayfaSlider.setBaslik(baslik);
		anaSayfaSlider.setSlogan(slogan);
		anaSayfaSlider.setSira(sira);
		anaSayfaSlider.setPath(path);
		anaSayfaSlider.setAktif(aktif);
		anaSayfaSlider.setMenu(menu != null ? menu.toEntity() : null);
		anaSayfaSlider.setContentDetay(contentDetay.getBytes());
		return anaSayfaSlider;
	}
}
