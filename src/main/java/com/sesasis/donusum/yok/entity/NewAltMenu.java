package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "new_alt_menu")
public class NewAltMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    String ad;
    @Column(name = "url")
    String url;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "alt_menu_group_id")
    private Long altMenuGroupId;

    @ManyToOne
    @JoinColumn(name = "alt_menü_id")
    @JsonIgnore
    private AltMenu altMenu;

    @OneToOne
    @JoinColumn(name = "dil_category_id")
    private GenelDilCategory genelDilCategory;

    @OneToMany(mappedBy = "newAltMenu")
    private List<MenuIcerik> menuIceriks;


}
