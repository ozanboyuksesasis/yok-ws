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
@Table(name = "onemli_bilgiler")
public class OnemliBilgiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sira_no")
    private Long siraNo;


    @Column(name = "baslik")
    private String baslik;

    @Column(name = "alt_baslik")
    private String altBaslik;

    @Lob
    @Column(name = "haber_icerik", columnDefinition = "TEXT")
    private String OnemliBilgilerIcerik;

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
    @JoinColumn(name = "haber_dil_category_id")
    private OnemliBilgilerDilCategory onemliBilgilerDilCategory;

}
