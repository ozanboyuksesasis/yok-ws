package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.dto.DomainDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Domain extends BaseModel<DomainDTO> {
	@Column(unique = true)
	String ad;
	String url;
	boolean anaDomainMi;

	@ManyToOne
	@JsonIgnore
	private Role role;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Menu> menuList;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Haber> haberList;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Duyuru> duyuruList;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Slider> sliders;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DomainLogo> domainLogos;

	@Override
	public DomainDTO toDTO() {
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setId(getId());
		domainDTO.setAd(ad);
		domainDTO.setUrl(url);
		domainDTO.setAnaDomainMi(anaDomainMi);
		return domainDTO;
	}
}
