package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenelDilCategoryDTO {

    private  Long id ;

    @NotBlank(message = "Hata: Kısaltma eki boş olamaz.")
    @Size(max = 3, message = "En fazla üç karakter girebilirsiniz.")
    private String kisaltmaEki;



    @NotBlank(message = "Hata : İsim boş olamaz.")
    private String name;

}
