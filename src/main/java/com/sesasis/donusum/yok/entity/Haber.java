package com.sesasis.donusum.yok.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "haber")
public class Haber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sira_no")
    private Long siraNo;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @Column(name = "baslik")
    private String baslik;

    @Column(name = "alt_baslik")
    private String altBaslik;

    @Lob
    @Column(name = "haber_icerik", columnDefinition = "TEXT")
    private String haberIcerik;

    @Column(name = "sayfa_url")
    private String sayfaUrl;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "domain_id")
    private Domain  domain;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "dil_category_id")
    private GenelDilCategory genelDilCategory;

}
