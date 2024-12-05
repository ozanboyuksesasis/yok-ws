package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuDTO extends BaseDTO<Menu> {
	String ad;
	Long domainId;
	String url;
	boolean anaSayfaMi;
	private int parentId ;
	private String label ;
	@JsonManagedReference
	private List<AltMenuDTO> altMenuDTOS;

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
