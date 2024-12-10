package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.entity.*;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import com.sesasis.donusum.yok.repository.MenuIcerikRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuIcerikService extends AbstractService<MenuIcerik, MenuIcerikRepository> implements IService<MenuIcerikDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final ModelMapperServiceImpl modelMapperService;
    private final MenuIcerikRepository menuIcerikRepository;
    private final MenuRepository menuRepository;
    private final AltMenuRepository altMenuRepository;

    public MenuIcerikService(MenuIcerikRepository repository, SecurityContextUtil securityContextUtil, ModelMapperServiceImpl modelMapperService,
                             MenuIcerikRepository menuIcerikRepository, MenuRepository menuRepository, AltMenuRepository altMenuRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.modelMapperService = modelMapperService;
        this.menuIcerikRepository = menuIcerikRepository;
        this.menuRepository = menuRepository;
        this.altMenuRepository = altMenuRepository;
    }


    @Override
    @Transactional
    public ApiResponse save(MenuIcerikDTO menuIcerikDTO) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        Menu menu = null;
        if (menuIcerikDTO.getMenuId()!=null){
            menuRepository.findOneByIdAndDomainId(menuIcerikDTO.getMenuId(), domain.getId());
        }

        AltMenu altMenu = null;
        if (menuIcerikDTO.getAltMenuId() != null) {
            altMenu = altMenuRepository.findOneByIdAndMenuId_DomainId(menuIcerikDTO.getAltMenuId(), domain.getId());
        }

        if (menu != null && altMenu != null) {
            return new ApiResponse<>(false, "Bir içerik aynı anda hem Menü hem Alt Menü ile ilişkilendirilemez.", null);
        }

        MenuIcerik menuIcerik = this.modelMapperService.request().map(menuIcerikDTO, MenuIcerik.class);
        menuIcerik.setMenu(menu);
        menuIcerik.setAltMenu(altMenu);
        menuIcerik.setBaslik(menuIcerikDTO.getBaslik());
        menuIcerik.setIcerik(menuIcerikDTO.getIcerik().getBytes());
        menuIcerikRepository.save(menuIcerik);
        return new ApiResponse(true, "İçerik eklendi.", null);

    }

    @Override
    public ApiResponse findAll() {
        // return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findAllByAltMenuAnaMenuDomainId(securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e->e.toDTO()).collect(Collectors.toList()));
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<MenuIcerik> menuIceriks = menuIcerikRepository.findAllByMenuDomainId(domain.getId());

        List<MenuIcerikDTO> dtos = menuIceriks.stream().map(menuIcerik -> {
            MenuIcerikDTO dto = new MenuIcerikDTO();
            dto.setBaslik(menuIcerik.getBaslik());
            dto.setDeleted(menuIcerik.getDeleted());
            dto.setId(menuIcerik.getId());
            dto.setMenuId(menuIcerik.getMenu().getId());
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

    public ApiResponse getListAltMenuIcerik(){
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain==null){
            return new ApiResponse<>(false,"Domain bulunamadı.",null);

        }
        List<MenuIcerik> menuIceriks = menuIcerikRepository.findAllByAltMenuMenuDomainId(domain.getId());
        List<MenuIcerikDTO> dtos = menuIceriks.stream().map(menuIcerik -> {
            MenuIcerikDTO dto = new MenuIcerikDTO();
            dto.setAltMenuId(menuIcerik.getAltMenu().getId());
            dto.setId(menuIcerik.getId());
            dto.setBaslik(menuIcerik.getBaslik());
            dto.setDeleted(menuIcerik.getDeleted());
            dto.setIcerik(new String(menuIcerik.getIcerik(),StandardCharsets.UTF_8));
            return dto;
        }).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }




}
