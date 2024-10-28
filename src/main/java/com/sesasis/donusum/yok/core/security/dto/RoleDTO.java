package com.sesasis.donusum.yok.core.security.dto;

import com.sesasis.donusum.yok.core.domain.BaseDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO  {

    private Long id;
    @NotNull
    @NotBlank(message = "Hata : Boş değer girilemez.")
    private String ad;


}
