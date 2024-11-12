package com.sesasis.donusum.yok.core.security.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import lombok.Data;

@Data
public class RoleDTO extends BaseDTO<Role> {
    private String ad;

    @Override
    public Role toEntity() {
        Role role = new Role();
        role.setId(getId());
        role.setAd(ad);
        return role;
    }
}
