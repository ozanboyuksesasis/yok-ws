package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.entity.MenuIcerik;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAltMenuDTO {

    private Long id;

    @NotBlank(message = "İsim giriniz.")
    String ad;

    private Long domainId;
    private Long groupId;
    private Long altMenuGroupId;
    private Long altMenuId;

    @NotBlank(message = "Url giriniz.")
    String url;


    @NotBlank(message = "Dil kategorisi seçiniz.")
    private Long genelDilCategoryId;


    private List<MenuIcerikDTO> menuIcerikDTOS;
}
