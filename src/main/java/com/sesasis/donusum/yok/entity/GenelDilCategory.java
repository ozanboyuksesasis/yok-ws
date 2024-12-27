package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "genel_dil_category")
public class GenelDilCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kisaltma_adi", nullable = false)
    private String kisaltmaAdi;


    @Column(name = "ad", nullable = false)
    private String ad;



}
