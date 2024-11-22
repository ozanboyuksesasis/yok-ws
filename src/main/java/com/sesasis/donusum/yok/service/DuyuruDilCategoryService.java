package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.dto.DuyuruDilCategoryDTO;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.DuyuruDilCategory;
import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DuyuruDilCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DuyuruDilCategoryService implements IService<DuyuruDilCategoryDTO> {

    private final DuyuruDilCategoryRepository duyuruDilCategoryRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;

    @Override
    public ApiResponse save(DuyuruDilCategoryDTO duyuruDilCategoryDTO) {
        try {
            duyuruDilCategoryDTO.setName(duyuruDilCategoryDTO.getName().trim().toUpperCase());
            DuyuruDilCategory duyuruDilCategory = this.modelMapperServiceImpl.request().map(duyuruDilCategoryDTO, DuyuruDilCategory.class);
            duyuruDilCategoryRepository.save(duyuruDilCategory);
            DuyuruDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(duyuruDilCategory, DuyuruDilCategoryDTO.class);
            return new ApiResponse<>(true, "İşlem başarılı.", dto);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @Override
    public ApiResponse findAll() {
        List<DuyuruDilCategory> duyuruDilCategoryList = this.duyuruDilCategoryRepository.findAll();
        if (duyuruDilCategoryList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<DuyuruDilCategoryDTO> dtos = duyuruDilCategoryList.stream().map(duyuruDilCategory ->
                modelMapperServiceImpl.response().map(duyuruDilCategory, DuyuruDilCategoryDTO.class)).collect(Collectors.toList());


        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }


    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (duyuruDilCategoryRepository.existsById(id)) {
            duyuruDilCategoryRepository.deleteById(id);
        }
    }
}
