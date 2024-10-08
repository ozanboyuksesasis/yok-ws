package com.sesasis.donusum.yok.core.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;

public interface IService<T> {

    ApiResponse save(T t);

    ApiResponse findAll();

    ApiResponse findById(Long id);

    void deleteById(Long id);

}
