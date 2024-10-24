package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.entity.Domain;
import lombok.Data;

@Data
public class DomainDTO extends BaseDTO<Domain> {
	String ad;
	String url;
	boolean anaDomainMi;
	RoleDTO role;

	@Override
	public Domain toEntity() {
		Domain domain = new Domain();
		domain.setId(getId());
		domain.setAd(ad);
		domain.setUrl(url);
		domain.setAnaDomainMi(anaDomainMi);
		domain.setRole(role!= null ? role.toEntity() : null);
		return domain;
	}
}
