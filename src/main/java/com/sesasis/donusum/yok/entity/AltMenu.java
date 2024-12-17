package com.sesasis.donusum.yok.entity;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class AltMenu extends BaseModel<AltMenuDTO> {
	String ad;
	@Column(name = "url")
	String url;

	@Column(name = "group_id")
	private Long groupId;

	@ManyToOne
	@JoinColumn(name = "domain_id")
	private Domain domain;

	@OneToOne
	@JoinColumn(name = "dil_category_id")
	private GenelDilCategory genelDilCategory;

	@ManyToOne
	@JoinColumn(name = "ana_menu_id", referencedColumnName = "id", nullable = false)
	private Menu menu;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "menu",orphanRemoval = true)
	private List<MenuIcerik> menuIceriks;

	@OneToMany(mappedBy = "altMenu",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<NewAltMenu> newAltMenus;



	@Override
	public AltMenuDTO toDTO() {
		AltMenuDTO altMenuDTO = new AltMenuDTO();
		altMenuDTO.setId(getId());
		altMenuDTO.setAd(ad);
		altMenuDTO.setUrl(url);
		altMenuDTO.setMenuDTO(menu != null ? menu.toDTO() : null);
		return altMenuDTO;
	}
}
