package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotografDTO {

    private Long id;

    private String imageUrl;


}
