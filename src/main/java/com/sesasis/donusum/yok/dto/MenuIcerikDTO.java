package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import lombok.Data;

import javax.transaction.Transactional;

@Data
@Transactional
public class MenuIcerikDTO extends BaseDTO<MenuIcerik> {
	String baslik;
	String icerik;

	@JsonBackReference
	AltMenuDTO altMenu;

	@Override
	public MenuIcerik toEntity() {
		MenuIcerik menuIcerik = new MenuIcerik();
		menuIcerik.setId(getId());
		menuIcerik.setIcerik(icerik.getBytes());
		menuIcerik.setAltMenu(altMenu != null ? altMenu.toEntity() : null);
		return menuIcerik;
	}
}
