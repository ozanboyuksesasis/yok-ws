package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import javax.persistence.*;

@Data
public class MenuDTO  {


	private int id ;

	private int parentId ;

	private String label ;

	private String isim;

	private  String menuUrl;

	private boolean anaSayfaMi;

	private Long domainID;


}


