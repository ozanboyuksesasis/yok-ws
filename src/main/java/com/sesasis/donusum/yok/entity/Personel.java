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
@Table(name = "personeller")
public class Personel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "unvan")
    private String unvan;

    @Column(name = "isim")
    private String isim;

    @Column(name = "email")
    private String email;

    @Column(name = "soyisim")
    private String soyisim;

    @Column(name = "kimlik_numarası", nullable = false, unique = true)
    private String kimlikNumarasi;

    @Column(name = "fotograf_url")
    private String fotografUrl;

    @Column(name = "profilUrl")
    private String profilUrl;

    @Column(name = "aktif")
    private Boolean aktif;

    @OneToMany(mappedBy = "personel", cascade = CascadeType.ALL)
    private List<GorevDonemi> gorevDonemleri;

    @ManyToOne
    @JoinColumn(name = "idaribirim_id",nullable = false)
    private IdariBirim idariBirim;

    @ManyToOne
    @JoinColumn(name = "personel_görev_id",nullable = false)
    private Gorev gorev;

}
