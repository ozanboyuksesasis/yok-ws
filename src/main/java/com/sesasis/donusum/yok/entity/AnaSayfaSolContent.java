package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.AnaSayfaSolContentDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Entity
@Data
public class AnaSayfaSolContent extends BaseModel<AnaSayfaSolContentDTO> {
	String baslik;
	int sira=0;
	boolean aktif;
	Date contentTarihi;
	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] contentDetay;
	@ManyToOne
	Menu menu;//ana sayfa olan menu ile ilişkilendirilecek

	@Override
	public AnaSayfaSolContentDTO toDTO() {
		AnaSayfaSolContentDTO anaSayfaSolContentDTO = new AnaSayfaSolContentDTO();
		anaSayfaSolContentDTO.setId(getId());
		anaSayfaSolContentDTO.setBaslik(baslik);
		anaSayfaSolContentDTO.setSira(sira);
		anaSayfaSolContentDTO.setContentTarihi(contentTarihi);
		anaSayfaSolContentDTO.setContentDetay(new String(contentDetay, StandardCharsets.UTF_8));
		anaSayfaSolContentDTO.setMenu(menu != null ? menu.toDTO() : null);
		return anaSayfaSolContentDTO;
	}
}
