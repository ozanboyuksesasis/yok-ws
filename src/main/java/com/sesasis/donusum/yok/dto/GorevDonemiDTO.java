package com.sesasis.donusum.yok.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GorevDonemiDTO {

    private Long id;
    private LocalDate girisTarihi;
    private LocalDate cikisTarihi;
    private Long personelId;
}
