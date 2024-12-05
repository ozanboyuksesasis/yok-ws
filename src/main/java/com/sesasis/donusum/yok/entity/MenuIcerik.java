package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.nio.charset.StandardCharsets;

@Data
@Entity
public class MenuIcerik extends BaseModel<MenuIcerikDTO> {

	private String	baslik;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] icerik;

	@OneToOne
	@JsonBackReference
	AltMenu altMenu;

	@Override
	public MenuIcerikDTO toDTO() {
		MenuIcerikDTO menuIcerikDTO = new MenuIcerikDTO();
		menuIcerikDTO.setId(getId());
		menuIcerikDTO.setIcerik(new String(icerik, StandardCharsets.UTF_8));
		menuIcerikDTO.setAltMenu(altMenu != null ? altMenu.toDTO() : null);
		return menuIcerikDTO;
	}
}
