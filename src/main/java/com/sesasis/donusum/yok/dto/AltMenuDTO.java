package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class AltMenuDTO {

	private Long id;

	private String ad;

	@URL(message = "Geçerli bir URL giriniz.")
	private String url;

	private Long menuId;

	private Long newDomainId;


}
