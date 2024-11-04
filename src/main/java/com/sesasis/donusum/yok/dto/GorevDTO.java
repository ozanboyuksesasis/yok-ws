package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GorevDTO {

    private Long Id;

    @NotBlank(message = "Hata : İsim boş bırakılamaz.")
    private String name;

    private List<PersonalDTO> personals;
}
