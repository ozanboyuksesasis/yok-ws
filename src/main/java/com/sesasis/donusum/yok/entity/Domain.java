package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.dto.DomainDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Domain extends BaseModel<DomainDTO> {
	@Column(unique = true)
	String ad;
	String url;
	boolean anaDomainMi;

	@ManyToOne
	@JsonIgnore
	private Role role;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ana_baslik_id")
	private AnaBaslik anaBaslik;


	@OneToMany(mappedBy = "domain", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Menu> menus;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<HaberDilCategory> haberDilCategories;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DuyuruDilCategory> duyuruDilCategories;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SliderDilCategory> sliderDilCategories;

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
