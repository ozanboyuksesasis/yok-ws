package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import com.sesasis.donusum.yok.repository.DomainRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AltMenuService extends AbstractService<AltMenu, AltMenuRepository> implements IService<AltMenuDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final AltMenuRepository altMenuRepository;
    private final DomainRepository domainRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final MenuRepository menuRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;

    public AltMenuService(AltMenuRepository repository, SecurityContextUtil securityContextUtil,
                          AltMenuRepository altMenuRepository, DomainRepository domainRepository, ModelMapperServiceImpl modelMapperService, MenuRepository menuRepository, GenelDilCategoryRepository genelDilCategoryRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.altMenuRepository = altMenuRepository;
        this.domainRepository = domainRepository;
        this.modelMapperService = modelMapperService;
        this.menuRepository = menuRepository;
        this.genelDilCategoryRepository = genelDilCategoryRepository;
    }


    @Override
    @Transactional
    public ApiResponse save(AltMenuDTO altMenuDTO) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        Menu menu = menuRepository.findOneByIdAndDomainId(altMenuDTO.getMenuId(), domain.getId());
        if (menu == null) {
            return new ApiResponse<>(false, "Menü bulunamadı.", null);
        }
        AltMenu altMenu = modelMapperService.request().map(altMenuDTO, AltMenu.class);
        altMenu.setMenu(menu);

        altMenuRepository.save(altMenu);
        return new ApiResponse(true, "Alt Menü başarıyla kaydedildi: ", null);
    }

    public ApiResponse addListAltMenu(List<AltMenuDTO> altMenuDTO) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        Long count = altMenuRepository.findMaxGroupId().orElse(0L);

        List<AltMenu> altMenus = new ArrayList<>();
        for (AltMenuDTO altMenuDTOS : altMenuDTO) {
            AltMenu altMenu = new AltMenu();
            altMenu.setAd(altMenuDTOS.getAd());
            altMenu.setId(altMenuDTOS.getId());
            altMenu.setUrl(altMenuDTOS.getUrl());
            altMenu.setDeleted(altMenuDTOS.getDeleted());
            altMenu.setGroupId(count + 1);
            Menu menu = null;
            if (altMenuDTOS.getMenuId() != null) {
                menu = menuRepository.findOneByIdAndDomainId(altMenuDTOS.getMenuId(), domain.getId());
                if (menu == null) {
                    return new ApiResponse<>(false, "Menü bulunamadı.", null);
                }
            }
            altMenu.setMenu(menu);
            GenelDilCategory genelDilCategory = null;
            if (altMenuDTOS.getGenelDilCategoryId() != null) {
                genelDilCategory = genelDilCategoryRepository.findById(altMenuDTOS.getGenelDilCategoryId()).orElse(null);
                if (genelDilCategory == null) {
                    return new ApiResponse<>(false, "Dil kategorisi bulunamadı.", null);
                }
            }
            altMenu.setGenelDilCategory(genelDilCategory);
            altMenus.add(altMenu);
        }

        altMenuRepository.saveAll(altMenus);
        return null;
    }


    @Override
    public ApiResponse findAll() {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<AltMenu> altMenus = altMenuRepository.findAllByMenuDomainId(domain.getId());
        List<AltMenuDTO> dtos = altMenus.stream().map(altMenu -> this.modelMapperService.response().map(altMenu, AltMenuDTO.class)).collect(Collectors.toList());


        return new ApiResponse<>(true, "İşlem başarılı.", dtos);

    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
