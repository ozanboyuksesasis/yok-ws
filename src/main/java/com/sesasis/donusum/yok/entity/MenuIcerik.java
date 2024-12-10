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
	@JoinColumn(name = "menü_id")
	@JsonIgnore
	private Menu menu;

	@ManyToOne
	@JoinColumn(name = "alt_menü_id")
	private AltMenu altMenu;


	@Override
	public MenuIcerikDTO toDTO() {
		MenuIcerikDTO menuIcerikDTO = new MenuIcerikDTO();
		menuIcerikDTO.setBaslik(getBaslik());
		menuIcerikDTO.setId(getId());
		menuIcerikDTO.setIcerik(new String(icerik, StandardCharsets.UTF_8));
		return menuIcerikDTO;
	}
}
