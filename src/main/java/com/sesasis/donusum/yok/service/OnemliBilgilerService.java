package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.OnemliBilgilerDTO;
import com.sesasis.donusum.yok.dto.OnemliBilgilerDilCategoryDTO;
import com.sesasis.donusum.yok.entity.OnemliBilgilerDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.OnemliBilgilerDilCategoryRepository;
import com.sesasis.donusum.yok.repository.OnemliBilgilerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnemliBilgilerService implements IService<OnemliBilgilerDTO> {


    @Override
    public ApiResponse save(OnemliBilgilerDTO OnemliBilgilerDTO) {
       ;

        return new ApiResponse<>(true,"İşlem başarılı.",null);
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
