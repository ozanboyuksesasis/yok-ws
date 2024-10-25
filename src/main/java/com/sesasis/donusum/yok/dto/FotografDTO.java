package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotografDTO {

    private Long id;

    private Long siraNo;

    private String fotografUrl;

    private String kucukFotografUrl;

    private LocalDate createdDate;


}



