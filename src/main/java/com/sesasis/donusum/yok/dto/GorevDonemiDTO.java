package com.sesasis.donusum.yok.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GorevDonemiDTO {

    private Long personelId;

    private Long gorevDonemId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate girisTarihi;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate cikisTarihi;


}
