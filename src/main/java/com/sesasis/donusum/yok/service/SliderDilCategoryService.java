package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.SliderDTO;
import com.sesasis.donusum.yok.dto.SliderDilCategoryDTO;
import com.sesasis.donusum.yok.entity.Slider;
import com.sesasis.donusum.yok.entity.SliderDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.SliderDilCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SliderDilCategoryService implements IService<SliderDilCategoryDTO> {
   private final SliderDilCategoryRepository sliderDilCategoryRepository;
   private final ModelMapperServiceImpl modelMapperServiceImpl;



    @Override
    public ApiResponse save(SliderDilCategoryDTO sliderDilCategoryDTO) {

            SliderDilCategory sliderDilCategory = this.modelMapperServiceImpl.request().map(sliderDilCategoryDTO, SliderDilCategory.class);
            sliderDilCategoryRepository.save(sliderDilCategory);
            SliderDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(sliderDilCategory, SliderDilCategoryDTO.class);
            return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public ApiResponse findAll() {
        List<SliderDilCategory> sliderDilCategoryList = this.sliderDilCategoryRepository.findAll();
        if(sliderDilCategoryList.isEmpty()){
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<SliderDilCategoryDTO> dtoList = sliderDilCategoryList.stream().map(sliderDilCategory -> {
            SliderDilCategoryDTO sliderDilCategoryDTO = this.modelMapperServiceImpl.response().map( sliderDilCategory, SliderDilCategoryDTO.class );

            sliderDilCategoryDTO.setSlidersDto(sliderDilCategory.getSliders().stream().
                    sorted(Comparator.comparing(Slider::getSiraNo).reversed())
                    .collect(Collectors.toList()).stream()
                    .map(slider -> this.modelMapperServiceImpl.response()
                            .map(slider, SliderDTO.class)).collect(Collectors.toList()));
            return sliderDilCategoryDTO;
        }).collect(Collectors.toList());



        return new ApiResponse<>(true,"İşlem başarılı.",dtoList);
    }

    @Override
    public ApiResponse findById(Long id) {
      SliderDilCategory sliderDilCategory = this.sliderDilCategoryRepository.findById(id).orElse(null);
      if(sliderDilCategory == null){
          return new ApiResponse<>(false,"Kategori bulunamadı.",null);
      }
      SliderDilCategoryDTO dto = this.modelMapperServiceImpl.response().map(sliderDilCategory, SliderDilCategoryDTO.class);
      dto.setSlidersDto(sliderDilCategory.getSliders().stream().sorted(Comparator.comparing(Slider::getSiraNo).reversed())
              .collect(Collectors.toList()).stream().map(slider ->
              this.modelMapperServiceImpl.response().map( slider, SliderDTO.class)).collect(Collectors.toList()));

        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {

    }
}
