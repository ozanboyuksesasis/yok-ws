package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DuyuruHaberDTO;
import com.sesasis.donusum.yok.repository.DuyuruHaberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuyuruHaberService implements IService<DuyuruHaberDTO> {

    private final DuyuruHaberRepository duyuruHaberRepository;


    @Override
    public ApiResponse save(DuyuruHaberDTO duyuruHaberDTO) {
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
