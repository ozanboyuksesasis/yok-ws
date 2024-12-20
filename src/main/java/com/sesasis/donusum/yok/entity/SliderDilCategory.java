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
@Table(name = "sliders_dil_category")
public class SliderDilCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain ;

    @OneToMany(mappedBy = "sliderDilCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Slider> sliders;


}
