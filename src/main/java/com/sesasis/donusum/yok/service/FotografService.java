package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.FotografDTO;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.FotografRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
@RequiredArgsConstructor
@Service
public class FotografService implements IService<FotografDTO> {
    private final FotografRepository fotografRepository;
    private final ModelMapperServiceImpl modelMapperService;



    @Override
    public ApiResponse save(FotografDTO fotografDTO) {
    return null;
    }

    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }


}
