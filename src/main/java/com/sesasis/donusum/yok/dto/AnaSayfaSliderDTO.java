package com.sesasis.donusum.yok.dto;
import lombok.Data;

@Data
public class AnaSayfaSliderDTO  {
	private Long id;
	private String baslik;
	private String slogan;
	private int sira;
	private String path;
	private boolean aktif;
	private byte[] contentDetay;
	private Long menuId;


}
