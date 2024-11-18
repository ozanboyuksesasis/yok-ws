package com.sesasis.donusum.yok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "duyuru_dil_category")
public class DuyuruDilCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain ;

    @OneToMany(mappedBy = "duyuruDilCategory", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Duyuru> duyuruList;
}
