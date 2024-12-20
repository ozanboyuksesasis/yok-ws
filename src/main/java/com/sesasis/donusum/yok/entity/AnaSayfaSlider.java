package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.nio.charset.StandardCharsets;

@Entity
@Data
public class AnaSayfaSlider extends BaseModel<AnaSayfaSliderDTO> {
	String baslik;
	String slogan;
	int sira=0;
	String path;
	boolean aktif;
	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] contentDetay;

//	@ManyToOne(fetch = FetchType.LAZY)
//	Menu menu;//ana sayfa olan menu ile ilişkilendirilecek
@Override
public AnaSayfaSliderDTO toDTO() {
	AnaSayfaSliderDTO anaSayfaSliderDTO = new AnaSayfaSliderDTO();
	anaSayfaSliderDTO.setId(getId());
	anaSayfaSliderDTO.setBaslik(baslik);
	anaSayfaSliderDTO.setSlogan(slogan);
	anaSayfaSliderDTO.setSira(sira);
	anaSayfaSliderDTO.setPath(path);
	anaSayfaSliderDTO.setAktif(aktif);
	if (contentDetay != null) {
		anaSayfaSliderDTO.setContentDetay(new String(contentDetay, StandardCharsets.UTF_8));
	} else {
		anaSayfaSliderDTO.setContentDetay(null);
	}
	return anaSayfaSliderDTO;
}
}