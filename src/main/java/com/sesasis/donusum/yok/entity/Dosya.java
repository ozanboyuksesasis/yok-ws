package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Dosya")
@Entity
public class Dosya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "baslik")
    String baslik;

    @Column(name = "alt_baslik")
    String altBaslik;

    @Column(name = "sira_no")
    private Long siraNo;

    @Column(name = "aktif_mi")
    boolean aktifMi;

    @Lob
    @Column(name = "content_detay")
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] contentDetay;

    @ManyToOne
    @JoinColumn(name = "dosya_id")
    private Dosya dosya;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;


}
