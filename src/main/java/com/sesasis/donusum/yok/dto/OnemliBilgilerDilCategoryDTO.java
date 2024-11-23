package com.sesasis.donusum.yok.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OnemliBilgilerDilCategoryDTO {

    private Long id;

    @NotBlank(message = "Hata : İsim boş bırakılamaz.")
    private String name;

}
