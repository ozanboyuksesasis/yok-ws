package com.sesasis.donusum.yok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDomainDTO {


    private Long id;

    @NotBlank(message = "İsim alanı boş olamaz veya yalnızca boşluk içeremez.")
    private String isim;

    @NotBlank(message = "URL alanı boş olamaz veya yalnızca boşluk içeremez.")
    private String url;


    private boolean anaDomainMi;

    @NotNull(message = "Role ID alanı boş olamaz.")
    private Long roleId;

    private List<MenuDTO> menuList;

    private List<HaberDTO> duyuruHabers;

    private List<CategoryDto> categories;

    private List<SliderDTO> sliders;

    private List<DomainLogoDTO> domainLogos;
}
