package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.*;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO extends BaseDTO<Menu> {
	String ad;
	Long domainId;
	Long genelDilCategoryId;
	String url;
	private Long groupId;
	private boolean aktifMi;
	boolean anaSayfaMi ;
	private int parentId ;
	private String label ;


	@JsonManagedReference
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<MenuIcerikDTO> menuIcerikDTOS;


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
