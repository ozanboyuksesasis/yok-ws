package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "alt_menus")
public class AltMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "ad", nullable = false)
	private String ad;

	@Column(name = "url", unique = true, nullable = false)
	private String url;

	@ManyToOne
	@JoinColumn(name = "ana_menu_id", referencedColumnName = "id")
	private Menu anaMenu;

}
