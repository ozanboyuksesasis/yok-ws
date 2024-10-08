package com.sesasis.donusum.yok.core.domain;

import com.sesasis.donusum.yok.entity.Domain;
import lombok.Data;

import java.util.List;

/**
 * Sisteme giriş yapan kullanıcı ile ilgili bütün bilgileri içerir
 */
@Data
public class UserInfoDTO {
    private Long userId;
    private String userName;
    private List<String> roles;
    private Domain loggedDomain;
}
