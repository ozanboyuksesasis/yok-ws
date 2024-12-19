package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewAltMenuDTO {

    private Long id;

    @NotBlank(message = "İsim giriniz.")
    String ad;

    private Long domainId;
    private Long groupId;
    private Long altMenuGroupId;
    private Long altMenuId;
    private Long menuGroupId;
    private Long newAltMenuGroupId;

    @NotBlank(message = "Url giriniz.")
    String url;
    @NotBlank(message = "Dil kategorisi seçiniz.")
    private Long genelDilCategoryId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuIcerikDTO> menuIcerikDTOS;
}
