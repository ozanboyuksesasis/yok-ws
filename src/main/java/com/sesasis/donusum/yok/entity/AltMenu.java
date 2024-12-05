package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class AltMenu extends BaseModel<AltMenuDTO> {
	String ad;
	@Column(unique = true)
	String url;
	@ManyToOne
	@JoinColumn(name = "ana_menu_id", referencedColumnName = "id", nullable = false)
	private Menu anaMenu;

	@Override
	public AltMenuDTO toDTO() {
		AltMenuDTO altMenuDTO = new AltMenuDTO();
		altMenuDTO.setId(getId());
		altMenuDTO.setAd(ad);
		altMenuDTO.setUrl(url);
		altMenuDTO.setAnaMenu(anaMenu != null ? anaMenu.toDTO() : null);
		return altMenuDTO;
	}
}
