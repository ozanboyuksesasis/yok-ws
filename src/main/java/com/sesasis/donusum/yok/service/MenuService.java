package com.sesasis.donusum.yok.service;

import com.beust.jcommander.IParameterValidator;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
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
        if (!menuDTO.isAnaSayfaMi()){
            return new ApiResponse<>(false,"Sadece  ana sayfa tanımlanır.",null);
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
            menuDTO.setDomainId(menu.getDomain().getId());
            menuDTO.setGenelDilCategoryId(menu.getGenelDilCategory() != null ? menu.getGenelDilCategory().getId() : null);
            menuDTO.setUrl(menu.getUrl());
            menuDTO.setLabel(menu.getLabel());
            menuDTO.setParentId(menu.getParentId());
            menuDTO.setAnaSayfaMi(menu.isAnaSayfaMi());
            menuDTO.setGroupId(menu.getGroupId());
            menuDTO.setDeleted(menu.getDeleted());
            return menuDTO;
        }).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    public ApiResponse saveList(List<MenuDTO> menuDTOS) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        Long countSiraNo = menuRepository.findMaxGroupId().orElse(0L);
        List<Menu> menuList = menuDTOS.stream().map(dto -> {
            if (dto.getUrl() != null) {
                Boolean existByUrl = menuRepository.existsByUrlAndDomain_Id(dto.getUrl(), loggedDomain.getId());
                if (existByUrl) {
                    throw new IllegalArgumentException("Bu URL daha önce farklı menü'de  kullanılmış:" + dto.getUrl());
                }
            }
            Menu menu = new Menu();
            menu.setDeleted(dto.getDeleted());
            GenelDilCategory dilCategory = null;
            if (dto.getGenelDilCategoryId() != null) {
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
            }
            menu.setGenelDilCategory(dilCategory);
            menu.setAktifMi(dto.isAktifMi());
            menu.setDomain(loggedDomain);
            menu.setGroupId(countSiraNo + 1);
            menu.setAd(dto.getAd());
            menu.setAnaSayfaMi(dto.isAnaSayfaMi());
            menu.setLabel(dto.getLabel());
            menu.setParentId(dto.getParentId());
            menu.setId(dto.getId());
            menu.setUrl(dto.getUrl());
            return menu;
        }).collect(Collectors.toList());

        menuRepository.saveAll(menuList);
        return new ApiResponse<>(true, "Kayıt başarılı.", null);
    }

    public ApiResponse updateMenu(Long groupId ,List<MenuDTO> menuDTOS){
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Menu> updateMenu = menuRepository.findAllByGroupIdAndDomain_Id(groupId, loggedDomain.getId());
        if (updateMenu.isEmpty()) {
            return new ApiResponse(false, "GroupId ile eşleşen menü bulunamadı.", null);
        }

        for (MenuDTO dto :menuDTOS){

            Menu menu = updateMenu.stream().filter(m->m.getId()
                    .equals(dto.getId())).
                    findFirst().orElse(null);

            if (menu!=null){
                Boolean existByUrl = menuRepository.existsByUrlAndDomain_Id(dto.getUrl(), loggedDomain.getId());
            }
        }
    return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findById(id).get().toDTO());
    }

    @Override
    public void deleteById(Long groupId) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain==null){
            throw new RuntimeException("Domain bulunamadi.");
        }
        List<Menu> menu = menuRepository.findAllByGroupIdAndDomain_Id(groupId, domain.getId());
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
