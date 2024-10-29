package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuyuruDTO {

    private Long id;

    private Long newDomainId;

    private String baslık;

    private String ozet;

    private String detay;

    private String sayfaUrl;

    private LocalDate createdAt;


}
