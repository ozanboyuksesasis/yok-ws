package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ana_sayfa_slider")
public class AnaSayfaSlider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "baslik", nullable = false)
	private String baslik;

	@Column(name = "slogan", nullable = false)
	private String slogan;

	@Column(name = "sira", nullable = false)
	private int sira = 0;

	@Column(name = "path", nullable = false)
	private String path;

	@Column(name = "aktif", nullable = false)
	private boolean aktif;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	@Column(name = "content_detay")
	private byte[] contentDetay;

	@ManyToOne
	private Menu menu;

}
