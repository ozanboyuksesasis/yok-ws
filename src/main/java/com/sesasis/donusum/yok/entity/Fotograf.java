package com.sesasis.donusum.yok.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fotoğraflar")
public class Fotograf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "siraNo")
    private Long siraNo;

    @Column(name = "fotograf_url")
    @Lob
    private String fotografUrl;

    @Column(name = "kucukfotograf_url")
    private String kucukFotografUrl;

    @Column(name = "created_date")
    private LocalDate createdDate;

   /* @OneToMany(mappedBy = "fotograf")
    private List<DomainLogo> domainLogos;

    @OneToMany(mappedBy = "fotograf")
    private List<Menu> menuler;*/
}
