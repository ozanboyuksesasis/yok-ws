package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import lombok.Data;

@Data
public class DashboardMenuDTO extends BaseDTO<DashboardMenu> {

	String name;
	DashboardMenu parent;
	int number;
	String path;
	String iconName;

	@Override
	public DashboardMenu toEntity() {
		DashboardMenu dashboardMenu = new DashboardMenu();
		dashboardMenu.setId(getId());
		dashboardMenu.setName(name);
		dashboardMenu.setParent(parent);
		dashboardMenu.setNumber(number);
		dashboardMenu.setPath(path);
		dashboardMenu.setIconName(iconName);
		return dashboardMenu;
	}
}
