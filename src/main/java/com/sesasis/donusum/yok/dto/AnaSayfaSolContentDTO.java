package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.utils.CustomDateSerializer;
import com.sesasis.donusum.yok.entity.AnaSayfaSolContent;
import lombok.Data;

import java.util.Date;

@Data
public class AnaSayfaSolContentDTO extends BaseDTO<AnaSayfaSolContent> {
	String baslik;
	int sira=0;
	boolean aktif;
	@JsonSerialize(using = CustomDateSerializer.class)
	Date contentTarihi;
	String contentDetay;
	MenuDTO menu;

	@Override
	public AnaSayfaSolContent toEntity() {
		AnaSayfaSolContent anaSayfaSolContent = new AnaSayfaSolContent();
		anaSayfaSolContent.setId(getId());
		anaSayfaSolContent.setBaslik(baslik);
		anaSayfaSolContent.setSira(sira);
		anaSayfaSolContent.setContentTarihi(contentTarihi);
		anaSayfaSolContent.setContentDetay(contentDetay.getBytes());
		anaSayfaSolContent.setMenu(menu != null ? menu.toEntity() : null);
		return anaSayfaSolContent;
	}
}
