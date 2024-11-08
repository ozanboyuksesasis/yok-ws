package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.dto.HaberDilCategoryDTO;
import com.sesasis.donusum.yok.dto.NameDTO;
import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.HaberDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.HaberDilCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HaberDilCategoryService implements IService<HaberDilCategoryDTO> {

    private final HaberDilCategoryRepository haberDilCategoryRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;



    @Override
    public ApiResponse save(HaberDilCategoryDTO haberDilCategoryDTO) {
        try {
            haberDilCategoryDTO.setName(haberDilCategoryDTO.getName().trim().toUpperCase());
            HaberDilCategory haberDilCategory = this.modelMapperServiceImpl.request().map(haberDilCategoryDTO, HaberDilCategory.class);
            haberDilCategoryRepository.save(haberDilCategory);
            HaberDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(haberDilCategory, HaberDilCategoryDTO.class);
            return new ApiResponse<>(true, "İşlem başarılı.", dto);
        } catch (Exception e) {
            return new ApiResponse<>(false, "İşlem başarısız oldu: " + e.getMessage(),null);
        }
    }


    @Override
    public ApiResponse findAll() {
        List<HaberDilCategory> haberDilCategoryList = haberDilCategoryRepository.findAll();
        if (haberDilCategoryList.isEmpty()) {
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<HaberDilCategoryDTO> dtos = haberDilCategoryList.stream().map(haberDilCategory -> {
            HaberDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(haberDilCategory, HaberDilCategoryDTO.class);
            dto.setHaberList(haberDilCategory.getHaberList().stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılıdır.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        HaberDilCategory haberDilCategory = haberDilCategoryRepository.findById(id).orElse(null);
        if (haberDilCategory == null) {
            return new ApiResponse<>(false,"İşlem başarısız.",null);
        }
        List<Haber> siralamaHaberList = haberDilCategory.getHaberList().stream().sorted(Comparator.comparing(Haber::getSiraNo).reversed()).collect(Collectors.toList());

        HaberDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(haberDilCategory, HaberDilCategoryDTO.class);
        dto.setHaberList(siralamaHaberList.stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));

        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
        if (haberDilCategoryRepository.existsById(id)) {
            haberDilCategoryRepository.deleteById(id);
        }
    }


}
