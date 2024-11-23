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
public class OnemliBilgilerDilCategoryService implements IService<OnemliBilgilerDilCategoryDTO> {

    private final OnemliBilgilerDilCategoryRepository dilCategoryRepository;
    private final ModelMapperServiceImpl modelMapperService;


    @Override
    public ApiResponse save(OnemliBilgilerDilCategoryDTO onemliBilgilerDilCategoryDTO) {
            onemliBilgilerDilCategoryDTO.setName(onemliBilgilerDilCategoryDTO.getName().trim());
            OnemliBilgilerDilCategory dilCategory =this.modelMapperService.request()
                    .map(onemliBilgilerDilCategoryDTO,OnemliBilgilerDilCategory.class);
            dilCategoryRepository.save(dilCategory);

        return new ApiResponse<>(true,"İşlem başarılı.",null);
    }

    @Override
    public ApiResponse findAll() {
        List<OnemliBilgilerDilCategory> dilCategories = dilCategoryRepository.findAll();
        if (dilCategories.isEmpty()){
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<OnemliBilgilerDilCategoryDTO>dtos=dilCategories.stream().map(dilCategory ->
                this.modelMapperService.response().map(dilCategory, OnemliBilgilerDilCategoryDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    if (dilCategoryRepository.existsById(id)){
        dilCategoryRepository.deleteById(id);
    }
    }
}
