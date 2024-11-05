package com.sesasis.donusum.yok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.security.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "newdomains")
public class NewDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ad", unique = true, nullable = false)
    private String isim;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "ana_domain_mi", nullable = false)
    private boolean anaDomainMi;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "newDomain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<NewMenu> menuList;

    @OneToMany(mappedBy = "newDomain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Haber> haberList;

    @OneToMany(mappedBy = "newDomain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Duyuru> duyuruList;

    @OneToMany(mappedBy = "newDomain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Slider> sliders;

    @OneToMany(mappedBy = "newDomain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DomainLogo> domainLogos;
}

