package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.DashboardMenuDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class DashboardMenu extends BaseModel<DashboardMenuDTO> {

	@Column(unique = true)
	String name;
	@OneToOne
	DashboardMenu parent;
	int number;
	@Column(unique = true)
	String path;
	String iconName;

	@Override
	public DashboardMenuDTO toDTO() {
		DashboardMenuDTO dashboardMenuDTO = new DashboardMenuDTO();
		dashboardMenuDTO.setId(getId());
		dashboardMenuDTO.setName(name);
		dashboardMenuDTO.setParent(parent);
		dashboardMenuDTO.setNumber(number);
		dashboardMenuDTO.setPath(path);
		dashboardMenuDTO.setIconName(iconName);
		return dashboardMenuDTO;
	}
}
