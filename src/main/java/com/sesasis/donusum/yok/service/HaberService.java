package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.repository.HaberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HaberService implements IService<HaberDTO> {

    private final HaberRepository haberRepository;


    @Override
    public ApiResponse save(HaberDTO haberDTO) {
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
