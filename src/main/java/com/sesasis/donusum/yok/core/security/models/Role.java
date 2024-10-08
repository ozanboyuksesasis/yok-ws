package com.sesasis.donusum.yok.core.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseModel<RoleDTO> {
	@Column(unique = true)
	String ad;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "role_dashboard_menu",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "dashboard_menu_id"))
	List<DashboardMenu> dashboardMenuList = new ArrayList<>();

	@Override
	public RoleDTO toDTO() {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(getId());
		roleDTO.setAd(ad);
		return roleDTO;
	}
}