package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Menu extends BaseModel<MenuDTO> {
	String ad;
	String url;
	boolean anaSayfaMi;

	@ManyToOne
	Domain domain;

	private int parentId ;
	private String label ;

	@ManyToOne
	@JoinColumn(name = "fotograf_id")
	private Fotograf fotograf;



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
