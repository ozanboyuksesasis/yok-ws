package com.sesasis.donusum.yok.core.domain;

import lombok.Data;

@Data
public class BaseFilterRequest<T> {
    T filter;
    SortingDTO sorting;
}
