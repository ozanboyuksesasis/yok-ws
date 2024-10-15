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
@Table(name = "gorev_donemleri")
public class GorevDonemi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "giris_tarihi", nullable = false)
    private LocalDate girisTarihi;

    @Column(name = "cikis_tarihi")
    private LocalDate cikisTarihi;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;
}
