package com.sesasis.donusum.yok.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class AnaDomainSlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private String icerik;
    private int siraNo;

    @Lob
    private byte[] foto;

    @ManyToMany
    @JoinTable(
        name = "slider_dil",
        joinColumns = @JoinColumn(name = "slider_id"),
        inverseJoinColumns = @JoinColumn(name = "dil_id")
    )
    private List<GenelDilCategory> diller;
}