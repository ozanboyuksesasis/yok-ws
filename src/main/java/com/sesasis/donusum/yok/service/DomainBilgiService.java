package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Menu;
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
public class DomainBilgiService {
        private final DomainRepository domainRepository;
        private final HaberDilCategoryRepository haberDilCategoryRepository;
        private final DuyuruDilCategoryRepository duyuruDilCategoryRepository;
        private final SliderDilCategoryRepository sliderDilCategoryRepository;
        private final ModelMapperServiceImpl modelMapperServiceImpl;


        public ApiResponse getAllMenusDomainId(Long domainId){
            Domain domain = domainRepository.findById(domainId).orElse(null);
            if (domain == null){
                return new ApiResponse<>(false,"Domain bulunamadı.",null);
            }
            List<Menu> menu = domain.getMenuList();
            if (menu == null || menu.isEmpty()) {
                return new ApiResponse<>(false, "Menü bulunamadı.", null);
            }

            List<MenuDTO> dtos = menu.stream().map(localMenu ->
                    this.modelMapperServiceImpl.response().map(localMenu, MenuDTO.class))
                    .collect(Collectors.toList());

           return new ApiResponse<>(true,"İşlem başarılı.",dtos);
        }

}