package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdariBirimDTO {

    private Long id;
    private String name;
    private List<PersonalDTO>  personalDTOS;
}
