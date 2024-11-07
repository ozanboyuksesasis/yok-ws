package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HaberDilCategoryDTO {

    @NotBlank(message = "Hata : Kategori boş olamaz.")
    @Min(value = 2,message = "En az iki karakter kullanılmalıdır.")
    private String name;

    private List<HaberDTO> haberList;
}
