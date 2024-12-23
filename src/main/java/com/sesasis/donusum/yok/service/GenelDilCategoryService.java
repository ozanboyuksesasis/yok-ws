package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.GenelDilCategoryDTO;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenelDilCategoryService implements IService<GenelDilCategoryDTO> {

    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final DomainRepository domainRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;


    @Override
    public ApiResponse save(GenelDilCategoryDTO genelDilCategoryDTO) {
          genelDilCategoryDTO.setName(genelDilCategoryDTO.getName().trim().toUpperCase());
          genelDilCategoryDTO.setKisaltmaEki(genelDilCategoryDTO.getKisaltmaEki().trim().toUpperCase());
            GenelDilCategory genelDilCategory = this.modelMapperServiceImpl.request().map(genelDilCategoryDTO, GenelDilCategory.class);
           genelDilCategoryRepository.save(genelDilCategory);
            GenelDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(genelDilCategory, GenelDilCategoryDTO.class);
            return new ApiResponse<>(true, "İşlem başarılı.", dto);

    }


    @Override
    public ApiResponse findAll() {
        List<GenelDilCategory> genelDilCategories = genelDilCategoryRepository.findAll();
        if (genelDilCategories.isEmpty()) {
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<GenelDilCategoryDTO> dtos = genelDilCategories.stream().map(genelDilCategory ->
           this.modelMapperServiceImpl.response().map(genelDilCategory, GenelDilCategoryDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılıdır.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(id).orElse(null);
        if (genelDilCategory == null) {
            return new ApiResponse<>(false,"İşlem başarısız.",null);
        }
        GenelDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(genelDilCategory, GenelDilCategoryDTO.class);
        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
        if (genelDilCategoryRepository.existsById(id)) {
            genelDilCategoryRepository.deleteById(id);
        }
    }


}
