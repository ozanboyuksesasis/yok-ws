package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.security.models.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "domains")
public class Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "ad", unique = true, nullable = false)
	private String ad;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "ana_domain_mi", nullable = false)
	private boolean anaDomainMi;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	@JsonIgnore
	private Role role;

}
