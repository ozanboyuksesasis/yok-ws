package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class AltMenuDTO {

	private Long id;

	private String ad;

	private String url;

	private Long menuId;


}
