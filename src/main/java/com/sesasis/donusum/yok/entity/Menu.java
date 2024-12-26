package com.sesasis.donusum.yok.entity;

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

	@Column(name = "group_id")
	private Long groupId;

	@ManyToOne
	Domain domain;

	@Column(name = "aktif_mi")
	private boolean aktifMi;

	private int parentId ;
	private String label ;

	@OneToOne
	@JoinColumn(name = "dil_category_id")
	private GenelDilCategory genelDilCategory;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "menu",orphanRemoval = true)
	private List<MenuIcerik> menuIceriks;


	@Override
	public MenuDTO toDTO() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setGroupId(groupId);
		menuDTO.setDomainId(domain.getId());
		menuDTO.setId(getId());
		menuDTO.setGenelDilCategoryId(genelDilCategory != null ? genelDilCategory.getId() : null);
		menuDTO.setAd(ad);
		menuDTO.setUrl(url);
		menuDTO.setAnaSayfaMi(anaSayfaMi);
		return menuDTO;
	}
}
