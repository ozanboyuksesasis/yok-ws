package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Data
@Entity
public class MenuIcerik extends BaseModel<MenuIcerikDTO> {

	private String	baslik;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] icerik;

	@ManyToOne
	@JoinColumn(name = "domain_id")
	@JsonIgnore
	private Domain domain;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	@JsonIgnore
	private Menu menu;

	@ManyToOne
	@JoinColumn(name = "alt_menu_id")
	private AltMenu altMenu;

	@ManyToOne
	@JoinColumn(name = "new_alt_menu_id")
	private NewAltMenu newAltMenu;


	@Override
	public MenuIcerikDTO toDTO() {
		MenuIcerikDTO menuIcerikDTO = new MenuIcerikDTO();
		menuIcerikDTO.setBaslik(getBaslik());
		menuIcerikDTO.setId(getId());
		menuIcerikDTO.setIcerik(new String(icerik, StandardCharsets.UTF_8));
		return menuIcerikDTO;
	}
}
