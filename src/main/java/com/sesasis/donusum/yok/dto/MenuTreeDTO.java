package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeDTO  {
	String ad;
	Long domainId;
	Long genelDilCategoryId;
	String url;
	private Long childId;
	private boolean aktifMi;
	boolean anaSayfaMi ;
	private Long parentId ;
	private String label ;


	@JsonManagedReference
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<MenuIcerikDTO> menuIcerikDTOS;



}
