package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.*;
import com.sesasis.donusum.yok.entity.*;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.String;

@Service
@RequiredArgsConstructor
@Transactional
public class DomainWebService {
    private final DomainRepository domainRepository;
    private final SliderDilCategoryRepository sliderDilCategoryRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final MenuRepository menuRepository;
    private final AnaBaslikRepository anaBaslikRepository;


    public ApiResponse getAllIcerikDomainId(Long domainId) {

        List<Menu> menus = menuRepository.findAllByDomainId(domainId);
        if (menus.isEmpty()) {
            return new ApiResponse<>(false, "Menü listesi boş.", null);
        }

        List<MenuIcerik> iceriks = domainRepository.findAllByMenus(menus);

        List<MenuIcerikDTO> dtos = iceriks.stream().map(menuIcerik -> {
            MenuIcerikDTO dto = new MenuIcerikDTO();
            dto.setBaslik(menuIcerik.getBaslik());
            dto.setDeleted(menuIcerik.getDeleted());
            dto.setId(menuIcerik.getId());
            dto.setAltMenuId(menuIcerik.getAltMenu().getId());
            dto.setMenuId(menuIcerik.getMenu().getId());
            dto.setGenelDilCategoryId(menuIcerik.getGenelDilCategory().getId());
            dto.setMenuId(menuIcerik.getMenu().getId());
            dto.setIcerik(menuIcerik.getIcerik() != null ? new String(menuIcerik.getIcerik(), StandardCharsets.UTF_8) : null);
           // dto.setMenuDTO(menuIcerik.getMenu().toDTO());
            return dto;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    public ApiResponse getAllMenusDomainId(Long domainId) {

        Domain domain = domainRepository.findById(domainId).orElse(null);
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<Menu> menu = menuRepository.findAllByDomainId(domainId);
        List<MenuDTO> dtos = menu.stream().map(localMenu -> this.modelMapperServiceImpl.response().
                map(localMenu, MenuDTO.class)).collect(Collectors.toList());


        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    public ApiResponse getAllSlidersDomainId(Long domainId) {
        Domain domain = domainRepository.findById(domainId).orElse(null);
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<SliderDilCategory> sliderDilCategories = domain.getSliderDilCategories();
        if (sliderDilCategories == null || sliderDilCategories.isEmpty()) {
            return new ApiResponse<>(false, "Slider dil listesi bulunamadı.", null);
        }
        List<SliderDilCategoryDTO> dtos = sliderDilCategories.stream().map(localSlider ->
                        this.modelMapperServiceImpl.response().map(localSlider, SliderDilCategoryDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    public  ApiResponse getMenuAndAltMenuAndIcerik(Long domainId){

        Domain domain = domainRepository.findById(domainId).orElse(null);
        if (domain==null){
            return new ApiResponse<>(false,"Domain bulunamadı.",null);
        }
        List<MenuDTO> menus = domain.getMenus().stream().map(menu -> {
            MenuDTO dto = new MenuDTO();
            dto.setId(menu.getId());
            dto.setAd(menu.getAd());
            dto.setUrl(menu.getUrl());
            dto.setDomainId(menu.getDomain().getId());
            dto.setLabel(menu.getLabel());
            dto.setDeleted(menu.getDeleted());
            dto.setParentId(menu.getParentId());
            dto.setAnaSayfaMi(menu.isAnaSayfaMi());
            dto.setGenelDilCategoryId(menu.getGenelDilCategory()!=null ? menu.getGenelDilCategory().getId() : null);
            dto.setMenuIcerikDTOS(menu.getMenuIceriks().stream().map(menuIcerik -> menuIcerik.toDTO()).collect(Collectors.toList()));
            dto.setAltMenuDTOS(menu.getAltMenus().stream().map(altMenu -> {
                AltMenuDTO dto1 = new AltMenuDTO();
                dto1.setId(altMenu.getId());
                dto1.setUrl(altMenu.getUrl());
                dto1.setMenuGroupId(altMenu.getMenuGroupId());
                dto1.setMenuId(altMenu.getMenu().getId());
                dto1.setAd(altMenu.getAd());
                dto1.setGenelDilCategoryId(altMenu.getGenelDilCategory().getId());
                dto1.setDeleted(altMenu.getDeleted());
                dto1.setGroupId(altMenu.getGroupId());
                dto1.setNewAltMenuDTOS(altMenu.getNewAltMenus().stream().map(newAltMenu -> this.modelMapperServiceImpl.response().map(newAltMenu,NewAltMenuDTO.class)).collect(Collectors.toList()));
                return dto1;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",menus);
    }

    public ApiResponse getBaslikDomainId(Long domainId) {

        AnaBaslik anaBaslik = anaBaslikRepository.findOneByDomainId(domainId);
        AnaBaslikDTO dto = this.modelMapperServiceImpl.response().map(anaBaslik,AnaBaslikDTO.class);
        return new ApiResponse<>(true, "İşlem başarılı.", dto);
    }

}
