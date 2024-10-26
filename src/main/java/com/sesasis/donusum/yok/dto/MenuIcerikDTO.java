package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Data
public class MenuIcerikDTO {

	private Long id;

	private byte[] icerik;

	private Long altMenuID;
}
