package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import lombok.Data;

import java.util.List;

@Data
public class AltMenuDTO extends BaseDTO<AltMenu> {
	String ad;
	String url;
	private Long menuId;

	@JsonBackReference
	MenuDTO menuDTO;

	@Override
	public AltMenu toEntity() {
		AltMenu altMenu = new AltMenu();
		altMenu.setId(getId());
		altMenu.setAd(ad);
		altMenu.setUrl(url);
		altMenu.setMenu(menuDTO != null ? menuDTO.toEntity() : null);
		return altMenu;
	}
}
