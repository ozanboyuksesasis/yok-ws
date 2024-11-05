package com.sesasis.donusum.yok.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "new_menüler")
public class NewMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

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
    @JoinColumn(name = "new_domain_id")
    private NewDomain newDomain;

    @ManyToOne
    @JoinColumn(name = "fotograf_id")
    private Fotograf fotograf;

}
