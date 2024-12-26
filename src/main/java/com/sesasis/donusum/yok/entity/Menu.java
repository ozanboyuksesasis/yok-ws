package com.sesasis.donusum.yok.entity;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuDTO;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Menu extends BaseModel<MenuDTO> {
	String ad;
	String url;
	boolean anaSayfaMi;

	@Column(name = "child_id")
	private Long childId;

	@ManyToOne
	Domain domain;

	@Column(name = "aktif_mi")
	private boolean aktifMi;

	@Column(name = "parent_id")
	private Long parentId ;

	private String label ;

	@Column(name = "parent_id")
	private List<Menu> childMenu = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "dil_category_id")
	private GenelDilCategory genelDilCategory;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "menu",orphanRemoval = true)
	private List<MenuIcerik> menuIceriks;

	public void addChild(Menu child) {
		this.childMenu.add(child);
	}


	@Override
	public MenuDTO toDTO() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setChildId(childId);
		menuDTO.setDomainId(domain.getId());
		menuDTO.setId(getId());
		menuDTO.setGenelDilCategoryId(genelDilCategory != null ? genelDilCategory.getId() : null);
		menuDTO.setAd(ad);
		menuDTO.setUrl(url);
		return menuDTO;
	}
}
