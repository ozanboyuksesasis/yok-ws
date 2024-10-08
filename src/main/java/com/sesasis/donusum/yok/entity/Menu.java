package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuDTO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Menu extends BaseModel<MenuDTO> {
	String ad;
	String url;
	boolean anaSayfaMi;
	@ManyToOne
	Domain domain;

	@Override
	public MenuDTO toDTO() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(getId());
		menuDTO.setAd(ad);
		menuDTO.setUrl(url);
		menuDTO.setAnaSayfaMi(anaSayfaMi);
		return menuDTO;
	}
}
