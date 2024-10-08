package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.dto.DomainDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
