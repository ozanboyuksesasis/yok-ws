package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdariBirimDTO {

    private Long id;

    @NotBlank(message = "Hata : İsim boş olamaz.")
    private String name;

    private List<PersonalDTO>  personalDTOS;
}
