package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AltMenuDTO extends BaseDTO<AltMenu> {
	@NotBlank(message = "İsim giriniz.")
	String ad;

	@NotBlank(message = "Url giriniz.")
	String url;

	@NotBlank(message = "Menü seçimi yapınız.")
	private Long menuId;
	@NotBlank(message = "Dil kategorisi seçiniz.")
	private Long genelDilCategoryId;

	private Long groupId;


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
