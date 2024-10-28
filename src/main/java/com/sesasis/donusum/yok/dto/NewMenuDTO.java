package com.sesasis.donusum.yok.dto;

import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.entity.NewDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMenuDTO {
    private Long id ;

    @NotNull
    @NotBlank(message = "Hata : Domain id boş bırakılamaz.")
    private Long domainId;

    @NotNull
    @NotBlank(message = "Hata : Fotoğraf id boş bırakılamaz.")
    private Long fotografId;

    private int parentId ;

    private String label ;

    @NotNull
    @NotBlank(message = "Hata : İsim boş bırakılamaz.")
    private String isim;

    @NotNull
    @NotBlank(message = "Hata : Url boş bırakılamaz.")
    private  String menuUrl;

    private boolean anaSayfaMi;

    private List<AltMenuDTO> altMenus;
}
