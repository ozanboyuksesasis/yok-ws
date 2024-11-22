package com.sesasis.donusum.yok.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.entity.*;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
public class DomainDTO extends BaseDTO<Domain> {
    String ad;
    String url;
    boolean anaDomainMi;
    RoleDTO role;

    AnaBaslikDTO anaBaslikDTO;

    private List<MenuDTO> menuDTOS;


    private List<HaberDilCategoryDTO> haberDilCategoryDTOS;


    private List<DuyuruDilCategoryDTO> duyuruDilCategoryDTOS;


    private List<SliderDilCategoryDTO> sliderDilCategoryDTOS;

    @Override
    public Domain toEntity() {
        Domain domain = new Domain();
        domain.setId(getId());
        domain.setAd(ad);
        domain.setUrl(url);
        domain.setAnaDomainMi(anaDomainMi);
        domain.setRole(role!= null ? role.toEntity() : null);
        return domain;
    }
}