package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.entity.*;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuIcerikService extends AbstractService<MenuIcerik, MenuIcerikRepository> implements IService<MenuIcerikDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final ModelMapperServiceImpl modelMapperService;
    private final MenuIcerikRepository menuIcerikRepository;
    private final MenuRepository menuRepository;
    private final AltMenuRepository altMenuRepository;
    private final NewAltMenuRepository newAltMenuRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;

    public MenuIcerikService(MenuIcerikRepository repository, SecurityContextUtil securityContextUtil, ModelMapperServiceImpl modelMapperService,
                             MenuIcerikRepository menuIcerikRepository, MenuRepository menuRepository, AltMenuRepository altMenuRepository, NewAltMenuRepository newAltMenuRepository, GenelDilCategoryRepository genelDilCategoryRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.modelMapperService = modelMapperService;
        this.menuIcerikRepository = menuIcerikRepository;
        this.menuRepository = menuRepository;
        this.altMenuRepository = altMenuRepository;
        this.newAltMenuRepository = newAltMenuRepository;
        this.genelDilCategoryRepository = genelDilCategoryRepository;
    }


    @Override
    @Transactional
    public ApiResponse save(MenuIcerikDTO menuIcerikDTO) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        List<Menu> menus = new ArrayList<>();
        if (menuIcerikDTO.getMenuId() != null) {
            menus = menuRepository.findAllByDomainId(domain.getId());
        }
        Menu menu = menus.stream().filter(m -> m.getId().equals(menuIcerikDTO.getMenuId())).findFirst().orElse(null);

        AltMenu altMenu = null;
        if (menuIcerikDTO.getAltMenuId() != null) {
            altMenu = altMenuRepository.findOneByIdAndMenuId_DomainId(menuIcerikDTO.getAltMenuId(), domain.getId());
        }
        NewAltMenu newAltMenu = newAltMenuRepository.findOneByIdAndDomainId(menuIcerikDTO.getNewAltMenuId(), domain.getId());
        if (newAltMenu == null) {
            return new ApiResponse<>(false, "Yeni sütun alt menü bulunamadı.", null);
        }
        MenuIcerik menuIcerik = this.modelMapperService.request().map(menuIcerikDTO, MenuIcerik.class);
        menuIcerik.setMenu(menu);
        menuIcerik.setNewAltMenu(newAltMenu);
        menuIcerik.setDomain(domain);
        menuIcerik.setAltMenu(altMenu);
        menuIcerik.setBaslik(menuIcerikDTO.getBaslik());
        menuIcerik.setIcerik(menuIcerikDTO.getIcerik().getBytes());
        menuIcerikRepository.save(menuIcerik);
        return new ApiResponse(true, "İçerik eklendi.", null);
    }

    public ApiResponse addListIcerik(List<MenuIcerikDTO> menuIcerikDTOS, Long menuGroupId, Long altMenuGroupId, Long newAltMenuGroupId) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<Menu> menus = new ArrayList<>();
        List<AltMenu> altMenus = new ArrayList<>();
        List<NewAltMenu> newAltMenus = new ArrayList<>();
        List<MenuIcerik> menuIceriks = new ArrayList<>();
        if (menuGroupId != null) {
            menus = menuRepository.findAllByGroupIdAndDomain_Id(menuGroupId, domain.getId());
        }
        if (altMenuGroupId != null) {
            altMenus = altMenuRepository.findAllByGroupIdAndDomain_Id(altMenuGroupId, domain.getId());
        }
        if (newAltMenuGroupId != null) {
            newAltMenus = newAltMenuRepository.findAllByGroupIdAndDomain_Id(newAltMenuGroupId, domain.getId());
        }

        for (int i = 0; i < menuIcerikDTOS.size() || i < menus.size() || i < altMenus.size() || i < newAltMenus.size(); i++) {
            MenuIcerikDTO dto = menuIcerikDTOS.get(i);
            Menu menu = menus.get(i);
            AltMenu altMenu = null;
            if (altMenus.size() < 1) {
                continue;
            } else {
                altMenu = altMenus.get(i);
            }
            NewAltMenu newAltMenu = null;
            if (newAltMenus.size() < 1) {
                continue;
            } else {
                newAltMenu = newAltMenus.get(i);
            }
            GenelDilCategory dilCategory = null;
            if (dto.getGenelDilCategoryId() != null) {
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                if (dilCategory == null) {
                    return new ApiResponse<>(false, "Dil seçimi bulunamadı.", null);
                }
            }
            MenuIcerik menuIcerik = new MenuIcerik();
            menuIcerik.setBaslik(dto.getBaslik());
            menuIcerik.setIcerik(dto.getIcerik().getBytes());
            menuIcerik.setDeleted(dto.getDeleted());
            menuIcerik.setDomain(domain);
            menuIcerik.setMenu(menu);
            menuIcerik.setGenelDilCategory(dilCategory);
            menuIcerik.setAltMenu(altMenu);
            menuIcerik.setNewAltMenu(newAltMenu);
            menuIceriks.add(menuIcerik);
        }
        menuIcerikRepository.saveAll(menuIceriks);

        return new ApiResponse<>(false, "Kayıt başarılı.", null);

    }

    @Override
    public ApiResponse findAll() {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<MenuIcerik> menuIceriks = menuIcerikRepository.findAllByDomainId(domain.getId());

        List<MenuIcerikDTO> dtos = menuIceriks.stream().map(menuIcerik -> {
            MenuIcerikDTO dto = new MenuIcerikDTO();
            dto.setBaslik(menuIcerik.getBaslik());
            dto.setDeleted(menuIcerik.getDeleted());
            dto.setId(menuIcerik.getId());
            dto.setMenuId(menuIcerik.getMenu() != null ? menuIcerik.getMenu().getId() : null);
            dto.setAltMenuId(menuIcerik.getAltMenu() != null ? menuIcerik.getAltMenu().getId() : null);
            dto.setIcerik(menuIcerik.getIcerik() != null ? new String(menuIcerik.getIcerik(), StandardCharsets.UTF_8) : null);
            return dto;
        }).collect(Collectors.toList());


        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();

        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (menuIcerikRepository.existsById(id)) {
            menuIcerikRepository.deleteById(id);
        }
    }

    public ApiResponse getIcerikByAltMenuUrl(String altMenuUrl) {
        //  return new ApiResponse(true, MessageConstant.SUCCESS,getRepository().findOneByAltMenuAnaMenuDomainIdAndAltMenuUrl(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),altMenuUrl).toDTO());
        return null;
    }

    public ApiResponse getListAltMenuIcerik() {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);

        }
        List<MenuIcerik> menuIceriks = menuIcerikRepository.findAllByAltMenuMenuDomainId(domain.getId());
        List<MenuIcerikDTO> dtos = menuIceriks.stream().map(menuIcerik -> {
            MenuIcerikDTO dto = new MenuIcerikDTO();
            dto.setAltMenuId(menuIcerik.getAltMenu().getId());
            dto.setId(menuIcerik.getId());
            dto.setBaslik(menuIcerik.getBaslik());
            dto.setDeleted(menuIcerik.getDeleted());
            dto.setIcerik(new String(menuIcerik.getIcerik(), StandardCharsets.UTF_8));
            return dto;
        }).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }


}
