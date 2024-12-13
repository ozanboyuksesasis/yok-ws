package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService extends AbstractService<Menu, MenuRepository> implements IService<MenuDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final MenuRepository menuRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;

    public MenuService(MenuRepository repository, SecurityContextUtil securityContextUtil, MenuRepository menuRepository, GenelDilCategoryRepository genelDilCategoryRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.menuRepository = menuRepository;
        this.genelDilCategoryRepository = genelDilCategoryRepository;
    }


    @Override
    @Transactional
    public ApiResponse save(MenuDTO menuDTO) {

        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        Menu existMenu = getRepository().findOneByDomainIdAndAnaSayfaMi(loggedDomain.getId(), Boolean.TRUE);
        Boolean existByUrl = menuRepository.existsByUrl(menuDTO.getUrl());
        if (existMenu != null && menuDTO.isAnaSayfaMi()) {
            return new ApiResponse(false, "Sadece bir tane anasayfa tanımlayabilirsiniz.", null);
        }
        if (existByUrl) {
            return new ApiResponse<>(false, "Bu url kullanılmıştır.", null);
        }
        Menu menu = menuDTO.toEntity();
        menu.setDomain(loggedDomain);
        getRepository().save(menu);

        return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
    }

    @Override
    public ApiResponse findAll() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findAllByDomainId(loggedDomain.getId()).stream().map(e -> e.toDTO()).collect(Collectors.toList()));
    }


    public ApiResponse saveList(List<MenuDTO> menuDTOS){
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }

        List<Menu> menuList = menuDTOS.stream().map(dto -> {
            Menu menu = new Menu();
            menu.setDeleted(dto.getDeleted());
            GenelDilCategory dilCategory =null;
            if (dto.getGenelDilCategoryId() != null){
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
            }
            menu.setGenelDilCategory(dilCategory);
            menu.setDomain(loggedDomain);
            menu.setAd(dto.getAd());
            menu.setAnaSayfaMi(dto.isAnaSayfaMi());
            menu.setLabel(dto.getLabel());
            menu.setParentId(dto.getParentId());
            menu.setId(dto.getId());
            menu.setUrl(dto.getUrl());
            return menu;
        }).collect(Collectors.toList());

        menuRepository.saveAll(menuList);
        return new ApiResponse<>(false,"Kayıt başarılı.",null);
    }

    @Override
    public ApiResponse findById(Long id) {
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findById(id).get().toDTO());
    }

    @Override
    public void deleteById(Long menuId) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        Menu menu = menuRepository.findOneByIdAndDomainId(menuId, domain.getId());
        if (menu == null) {
            throw new IllegalArgumentException("Menü bulunamadı.");
        }
        menuRepository.deleteById(menu.getId());
    }

    public ApiResponse findAllWithoutAnasayfa() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findAllByDomainIdAndAnaSayfaMi(loggedDomain.getId(), Boolean.FALSE).stream().map(e -> e.toDTO()).collect(Collectors.toList()));
    }

    public ApiResponse findDomainAnasayfa() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        Menu anasayfa = getRepository().findOneByDomainIdAndAnaSayfaMi(loggedDomain.getId(), Boolean.TRUE);

        if (anasayfa == null) {
            return new ApiResponse(false, MessageConstant.ERROR, null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findOneByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(), Boolean.TRUE).toDTO());
    }

}
