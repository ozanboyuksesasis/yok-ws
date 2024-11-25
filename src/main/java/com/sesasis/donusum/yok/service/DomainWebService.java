package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.dto.SliderDilCategoryDTO;
import com.sesasis.donusum.yok.entity.AnaBaslik;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.entity.SliderDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DomainWebService {
        private final DomainRepository domainRepository;
        private final SliderDilCategoryRepository sliderDilCategoryRepository;
        private final ModelMapperServiceImpl modelMapperServiceImpl;


        public ApiResponse getAllMenusDomainId(Long domainId){
            Domain domain = domainRepository.findById(domainId).orElse(null);
            if (domain == null){
                return new ApiResponse<>(false,"Domain bulunamadı.",null);
            }
            List<Menu> menu = domain.getMenus();
            if (menu == null || menu.isEmpty()) {
                return new ApiResponse<>(false, "Menü bulunamadı.", null);
            }

            List<MenuDTO> dtos = menu.stream().map(localMenu ->
                    this.modelMapperServiceImpl.response().map(localMenu, MenuDTO.class))
                    .collect(Collectors.toList());

           return new ApiResponse<>(true,"İşlem başarılı.",dtos);
        }


      public ApiResponse getAllSlidersDomainId(Long domainId){
        Domain domain = domainRepository.findById(domainId).orElse(null);
        if (domain == null){
            return new ApiResponse<>(false,"Domain bulunamadı.",null);
        }
        List<SliderDilCategory> sliderDilCategories = domain.getSliderDilCategories();
        if (sliderDilCategories == null || sliderDilCategories.isEmpty()) {
            return new ApiResponse<>(false, "Slider dil listesi bulunamadı.", null);
        }
        List<SliderDilCategoryDTO> dtos = sliderDilCategories.stream().map(localSlider ->
                        this.modelMapperServiceImpl.response().map(localSlider, SliderDilCategoryDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    public ApiResponse getBaslikDomainId(Long domainId){
            Domain domain = domainRepository.findById(domainId).orElse(null);
            if (domain==null){
                return new ApiResponse<>(false,"Domain bulunamadı işlem başarısız.",null);
            }

            return new ApiResponse<>(true,"İşlem başarılı.",null);
    }




}
