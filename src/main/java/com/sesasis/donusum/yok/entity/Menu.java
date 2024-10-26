package com.sesasis.donusum.yok.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Menu  {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	int id ;

	@Column(name = "parent_id")
	private int parentId ;

	@Column(name = "label")
	private String label ;

	@Column(name = "isim")
	private String isim;

	@Column(name ="menu_url" )
	private  String menuUrl;

	@Column(name = "ana_sayfa_mi")
	private boolean anaSayfaMi;

	@ManyToOne
	@JoinColumn(name = "domain_id")
	private Domain domain;

	@ManyToOne
	@JoinColumn(name = "fotograf_id")
	private Fotograf fotograf;



}
