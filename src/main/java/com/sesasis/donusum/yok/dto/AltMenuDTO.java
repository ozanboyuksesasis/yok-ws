package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import lombok.Data;

@Data
public class AltMenuDTO extends BaseDTO<AltMenu> {
	String ad;
	String url;
	MenuDTO anaMenu;

	@Override
	public AltMenu toEntity() {
		AltMenu altMenu = new AltMenu();
		altMenu.setId(getId());
		altMenu.setAd(ad);
		altMenu.setUrl(url);
		altMenu.setAnaMenu(anaMenu != null ? anaMenu.toEntity() : null);
		return altMenu;
	}
}
