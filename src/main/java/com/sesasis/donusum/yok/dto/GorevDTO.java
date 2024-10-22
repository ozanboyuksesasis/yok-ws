package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GorevDTO {

    private Long Id;

    private String name;

    private List<PersonalDTO> personals;
}
