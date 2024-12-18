package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import lombok.Data;

import javax.transaction.Transactional;

@Data
@Transactional
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuIcerikDTO extends BaseDTO<MenuIcerik> {

	private Long menuId;
	private Long altMenuId;
	private Long newAltMenuId;

	String baslik;
	String icerik;

	@JsonBackReference
	private MenuDTO menu;

	@Override
	public MenuIcerik toEntity() {
		MenuIcerik menuIcerik = new MenuIcerik();
		menuIcerik.setBaslik(getBaslik());
		menuIcerik.setId(getId());
		menuIcerik.setIcerik(icerik.getBytes());
		return menuIcerik;
	}
}
