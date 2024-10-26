package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dashboard_menus")
public class DashboardMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private DashboardMenu parent;

	@Column(name = "number", nullable = false)
	private int number;

	@Column(name = "path", unique = true, nullable = false)
	private String path;

	@Column(name = "icon_name")
	private String iconName;
}
