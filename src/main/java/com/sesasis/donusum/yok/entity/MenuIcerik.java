package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "menü_id")
	@JsonIgnore
	private Menu menu;


	@Override
	public MenuIcerikDTO toDTO() {
		MenuIcerikDTO menuIcerikDTO = new MenuIcerikDTO();
		menuIcerikDTO.setBaslik(getBaslik());
		menuIcerikDTO.setId(getId());
		menuIcerikDTO.setIcerik(new String(icerik, StandardCharsets.UTF_8));
		return menuIcerikDTO;
	}
}
