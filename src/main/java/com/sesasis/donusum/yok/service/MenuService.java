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
import com.sesasis.donusum.yok.excepiton.MenuNotFoundException;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MenuService extends AbstractService<Menu, MenuRepository> implements IService<MenuDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final MenuRepository menuRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;
    private final Validator validator;

    public MenuService(MenuRepository repository, SecurityContextUtil securityContextUtil, MenuRepository menuRepository, GenelDilCategoryRepository genelDilCategoryRepository, Validator validator) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.menuRepository = menuRepository;
        this.genelDilCategoryRepository = genelDilCategoryRepository;
        this.validator = validator;
    }


    @Override
    @Transactional
    public ApiResponse save(MenuDTO menuDTO) {

        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        Menu existMenu = getRepository().findOneByDomainIdAndAnaSayfaMi(loggedDomain.getId(), Boolean.TRUE);
        if (existMenu != null && menuDTO.isAnaSayfaMi()) {
            return new ApiResponse(false, "Sadece bir tane anasayfa tanımlayabilirsiniz.", null);
        }
        if (!menuDTO.isAnaSayfaMi()) {
            return new ApiResponse<>(false, "Sadece  ana sayfa tanımlanır.", null);
        }
        Boolean existByUrl = menuRepository.existsByUrlAndDomain_Id(menuDTO.getUrl(), loggedDomain.getId());
        if (existByUrl) {
            return new ApiResponse<>(false, "Bu url daha önce kullanılmış.", null);
        }
        Menu menu = menuDTO.toEntity();
        if (menuDTO.isAnaSayfaMi()) {
            menu.setGenelDilCategory(null);
        }

        menu.setDomain(loggedDomain);
        getRepository().save(menu);

        return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
    }

    @Override
    public ApiResponse findAll() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Menu> menus = menuRepository.findAllByDomainId(loggedDomain.getId());
        List<MenuDTO> dtos = menus.stream().map(menu -> {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(menu.getId());
            menuDTO.setAd(menu.getAd());
            menuDTO.setAktifMi(menu.isAktifMi());
            menuDTO.setDomainId(menu.getDomain().getId());
            menuDTO.setGenelDilCategoryId(menu.getGenelDilCategory() != null ? menu.getGenelDilCategory().getId() : null);
            menuDTO.setUrl(menu.getUrl());
            menuDTO.setLabel(menu.getLabel());
            menuDTO.setParentId(menu.getParentId());
            menuDTO.setAnaSayfaMi(menu.isAnaSayfaMi());
            menuDTO.setChildId(menu.getChildId());
            return menuDTO;
        }).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    public ApiResponse saveList(List<MenuDTO> menuDTOS) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Menu> menuList = menuDTOS.stream().map(dto -> {
            if (dto.getUrl() != null) {
                Boolean existByUrl = menuRepository.existsByUrlAndDomain_Id(dto.getUrl(), loggedDomain.getId());
                if (existByUrl) {
                    throw new IllegalArgumentException("Bu URL daha önce farklı menü'de  kullanılmış:" + dto.getUrl());
                }
            }
            GenelDilCategory dilCategory = null;
            if (dto.getGenelDilCategoryId() != null) {
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                if (dilCategory == null) {
                    throw new RuntimeException("Dil kategorisi bulunamadı.");
                }
            }
            Long parentId = menuRepository.findMaxParentId().orElse(0L);
            Menu menu = new Menu();
            menu.setAnaSayfaMi(dto.isAnaSayfaMi());
            if (dto.isAnaSayfaMi()) {
                menu.setParentId(parentId + 1);
                menu.setChildId(null);
                menu.setGenelDilCategory(dilCategory);
                menu.setAktifMi(dto.isAktifMi());
                menu.setDomain(loggedDomain);
                menu.setAd(dto.getAd());
                menu.setLabel(dto.getLabel());
                menu.setUrl(dto.getUrl());
            }
            return menu;
        }).collect(Collectors.toList());

        menuRepository.saveAll(menuList);
        return new ApiResponse<>(true, "Kayıt başarılı.", null);
    }


    public ApiResponse saveChild(List<MenuDTO> menuDTOS, Long parentGroupId) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Menu> parentMenus = new ArrayList<>();
        if (parentGroupId != null) {
            parentMenus = menuRepository.findAllByParentIdAndDomain_Id(parentGroupId, loggedDomain.getId());
            if (parentMenus == null || parentMenus.isEmpty()) {
                throw new MenuNotFoundException("Ana menü bulunamadı.");
            }
        }
        Long parentId=parentMenus.get(0).getParentId();
        List<Menu> menuList = menuDTOS.stream().map(dto -> {
            if (dto.getUrl() != null) {
                Boolean existByUrl = menuRepository.existsByUrlAndDomain_Id(dto.getUrl(), loggedDomain.getId());
                if (existByUrl) {
                    throw new IllegalArgumentException("Bu URL daha önce farklı menü'de kullanılmış: " + dto.getUrl());
                }
            }
            GenelDilCategory dilCategory = null;
            if (dto.getGenelDilCategoryId() != null) {
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                if (dilCategory == null) {
                    throw new RuntimeException("Dil kategorisi bulunamadı.");
                }
            }

            Menu menu = new Menu();
            if (!dto.isAnaSayfaMi()) {
                Long childId = menuRepository.findMaxChildId().orElse(0L);
                menu.setDomain(loggedDomain);
                menu.setChildId(childId + 1);
                menu.setParentId(parentId);
                menu.setGenelDilCategory(dilCategory);
                menu.setAktifMi(dto.isAktifMi());
                menu.setAd(dto.getAd());
                menu.setLabel(dto.getLabel());
                menu.setUrl(dto.getUrl());
            }

            return menu;
        }).collect(Collectors.toList());

        menuRepository.saveAll(menuList);
        return new ApiResponse<>(true, "Kayıt başarılı.", null);

    }

    public ApiResponse updateMenu(List<MenuDTO> menuDTOS, Long groupId) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Menu> updateMenu = menuRepository.findAllByChildIdAndDomain_Id(groupId, loggedDomain.getId());
        if (updateMenu.isEmpty()) {
            return new ApiResponse(false, "GroupId ile eşleşen menü bulunamadı.", null);
        }
        List<Menu> menus = updateMenu.stream().map(menu -> {
            for (MenuDTO dto : menuDTOS) {
                menu.setAd(dto.getAd());
                menu.setLabel(dto.getLabel());
                menu.setUrl(dto.getUrl());
                menu.setDomain(loggedDomain);
                menu.setAd(dto.getAd());
                GenelDilCategory genelDilCategory = null;
                if (dto.getGenelDilCategoryId() != null) {
                    genelDilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                    if (genelDilCategory == null) {
                        throw new IllegalArgumentException("Hata: Dil seçimi bulunamadı.");
                    }
                }
                menu.setGenelDilCategory(genelDilCategory);
                menu.setAktifMi(dto.isAktifMi());
                menu.setParentId(dto.getParentId());
            }
            return menu;
        }).collect(Collectors.toList());
        menuRepository.saveAll(menus);

        return new ApiResponse<>(true, "Güncelleme başarılı.", null);
    }

    @Override
    public ApiResponse findById(Long id) {
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findById(id).get().toDTO());
    }

    @Override
    public void deleteById(Long groupId) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            throw new RuntimeException("Domain bulunamadi.");
        }
        List<Menu> menu = menuRepository.findAllByChildIdAndDomain_Id(groupId, domain.getId());
        if (menu.isEmpty()) {
            throw new RuntimeException("Menü grubu bulunamadi.");
        }
        menuRepository.deleteAll(menu);
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
