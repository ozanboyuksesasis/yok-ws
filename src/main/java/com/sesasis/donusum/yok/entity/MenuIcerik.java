package com.sesasis.donusum.yok.entity;

import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_icerik")
public class MenuIcerik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;


	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] icerik;

	@OneToOne
	AltMenu altMenu;



}
