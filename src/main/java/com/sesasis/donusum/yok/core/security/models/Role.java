package com.sesasis.donusum.yok.core.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String ad;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "role_dashboard_menu",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "dashboard_menu_id"))
	private List<DashboardMenu> dashboardMenuList = new ArrayList<>();

}
