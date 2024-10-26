package com.sesasis.donusum.yok.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ana_sayfa_sol_content")
public class AnaSayfaSolContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "baslik", nullable = false)
	private String baslik;

	@Column(name = "sira", nullable = false)
	private int sira = 0;

	@Column(name = "aktif", nullable = false)
	private boolean aktif;

	@Temporal(TemporalType.DATE)
	@Column(name = "content_tarihi", nullable = false)
	private Date contentTarihi;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	@Column(name = "content_detay")
	private byte[] contentDetay;

	@ManyToOne
	@JoinColumn(name = "menu_id", referencedColumnName = "id")
	private Menu menu;
}
