package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "duyurular")
public class Duyuru {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "baslık")
    private String baslık;

    @Column(name = "ozet")
    private String ozet;

    @Column(name = "detay")
    private String detay;

    @Column(name = "sayfa_url")
    private String sayfaUrl;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "new_domain_id")
    private NewDomain  newDomain;
}
