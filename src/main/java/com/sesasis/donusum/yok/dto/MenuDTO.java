package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

@Data
public class MenuDTO extends BaseDTO<Menu> {
	String ad;
	String url;
	boolean anaSayfaMi;

	@Override
	public Menu toEntity() {
		Menu menu = new Menu();
		menu.setId(getId());
		menu.setAd(ad);
		menu.setUrl(url);
		menu.setAnaSayfaMi(anaSayfaMi);
		return menu;
	}
}
