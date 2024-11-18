package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.entity.Slider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SliderDilCategoryDTO {

    private Long domainId;

    private Long id;

    @NotBlank(message = "Hata : İsim boş bırakılamaz.")
    private String name;



}
