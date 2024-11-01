package com.sesasis.donusum.yok.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String fotografUrl;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "fotograf",cascade = CascadeType.ALL)
    private List<DomainLogo> domainLogos;

    @OneToMany(mappedBy = "fotograf",cascade = CascadeType.ALL)
    private List<NewMenu> menuler;

    @ManyToOne
    @JoinColumn(name = "slider_id")
    @JsonIgnore
    private Slider slider;


}
