package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.NewAltMenuDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.entity.NewAltMenu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.NewAltMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewAltMenuService implements IService<NewAltMenuDTO> {

    private final ModelMapperServiceImpl modelMapperService;
    private final NewAltMenuRepository newAltMenuRepository;
    private final SecurityContextUtil securityContextUtil;
    private final AltMenuRepository altMenuRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;

    @Override
    public ApiResponse save(NewAltMenuDTO newAltMenuDTO) {

        return null;
    }

    public ApiResponse addListNewAltMenu(List<NewAltMenuDTO> newAltMenuDTOS, Long altMenuGroupId) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<NewAltMenu> newAltMenus = new ArrayList<>();
        Long count = newAltMenuRepository.findMaxGroupId().orElse(0L);
        if (newAltMenuDTOS == null || newAltMenuDTOS.isEmpty()) {
            return new ApiResponse<>(false, "DTO listesi boş geldi.", null);
        }
        List<AltMenu> altMenus = altMenuRepository.findAllByGroupIdAndDomain_Id(altMenuGroupId, loggedDomain.getId());
        if (altMenus == null || altMenus.isEmpty()) {
            return new ApiResponse<>(false, "Alt menü bulunamadı.", null);
        }
        for (int i = 0; i < newAltMenuDTOS.size() && i < altMenus.size(); i++) {
            NewAltMenuDTO dto = newAltMenuDTOS.get(i);
            AltMenu altMenu = altMenus.get(i);
            NewAltMenu newAltMenu = new NewAltMenu();
            newAltMenu.setAd(dto.getAd());
            newAltMenu.setAltMenuGroupId(altMenu.getGroupId());
            newAltMenu.setUrl(dto.getUrl());
            newAltMenu.setAltMenu(altMenu); // HATA DÜZELTİLDİ
            newAltMenu.setGroupId(count + 1);
            newAltMenu.setDomain(loggedDomain);
            GenelDilCategory genelDilCategory;
            if (dto.getGenelDilCategoryId() != null) {
                genelDilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                if (genelDilCategory == null) {
                    return new ApiResponse<>(false, "Dil bulunamadı.", null);
                }
                newAltMenu.setGenelDilCategory(genelDilCategory);
            }
            newAltMenus.add(newAltMenu);
        }
        newAltMenuRepository.saveAll(newAltMenus);

        return new ApiResponse<>(true, "Diğer alt menü kayıt başarılı.", null);
    }

    @Override
    public ApiResponse findAll() {

        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<NewAltMenu> newAltMenus = newAltMenuRepository.findAllByDomain_Id(loggedDomain.getId());
        List<NewAltMenuDTO> dtos = newAltMenus.stream().map(newAltMenu -> {
            NewAltMenuDTO dto = new NewAltMenuDTO();
            dto.setId(newAltMenu.getId());
            dto.setUrl(newAltMenu.getUrl());
            dto.setAd(newAltMenu.getAd());
            dto.setAltMenuId(newAltMenu.getAltMenu().getId());
            dto.setAltMenuGroupId(newAltMenu.getAltMenu().getGroupId());
            dto.setDomainId(loggedDomain.getId());
            dto.setGenelDilCategoryId(newAltMenu.getGenelDilCategory().getId());
            dto.setAltMenuId(newAltMenu.getAltMenu().getId());

            return dto;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
