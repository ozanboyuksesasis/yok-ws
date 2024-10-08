package com.sesasis.donusum.yok.core.security.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.models.User;
import lombok.Data;

import java.util.*;

@Data
public class UserDTO extends BaseDTO<User> {
    private Long id;
    private String ad;
    private String soyad;
    private String username;
    private String email;
    private String password;
    private boolean aktif;
    private List<Role> roleList = new ArrayList<>();

    @Override
    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setAd(ad);
        user.setSoyad(soyad);
        user.setUsername(username);
        user.setEmail(email);
        user.setAktif(aktif);
        user.setRoleList(roleList);
        return user;
    }
}
