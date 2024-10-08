package com.sesasis.donusum.yok.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO<T extends BaseModel> {
    private Long id;

    private int deleted;

    public abstract T toEntity();

}
