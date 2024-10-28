package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sliders")
public class Slider {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sira_no")
    private Long siraNo;

    @Column(name = "baslik")
    private String baslik;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "sayfa_url")
    private String sayfaUrl;

    @ManyToOne
    @JoinColumn(name = "new_domain_id")
    private NewDomain  newDomain;


    @OneToMany(mappedBy = "slider")
    private List<Fotograf> fotograflar;


    
}
