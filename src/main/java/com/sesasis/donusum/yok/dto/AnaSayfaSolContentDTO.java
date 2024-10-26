package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.utils.CustomDateSerializer;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import com.sesasis.donusum.yok.entity.AnaSayfaSolContent;
import com.sesasis.donusum.yok.entity.Menu;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class AnaSayfaSolContentDTO  {
	private Long id;
	private String baslik;
	private int sira;
	private boolean aktif;
	private Date contentTarihi;
	private byte[] contentDetay;
	private Long menuId;
}
