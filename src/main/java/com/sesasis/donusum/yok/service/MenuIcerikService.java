package com.sesasis.donusum.yok.service;
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
    private final GenelDilCategoryRepository genelDilCategoryRepository;
    public MenuIcerikService(MenuIcerikRepository repository, SecurityContextUtil securityContextUtil, ModelMapperServiceImpl modelMapperService,
                             MenuIcerikRepository menuIcerikRepository, MenuRepository menuRepository, GenelDilCategoryRepository genelDilCategoryRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.modelMapperService = modelMapperService;
        this.menuIcerikRepository = menuIcerikRepository;
        this.menuRepository = menuRepository;
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
        MenuIcerik menuIcerik = this.modelMapperService.request().map(menuIcerikDTO, MenuIcerik.class);
        menuIcerik.setMenu(menu);
        menuIcerik.setAccordion(menuIcerikDTO.getAccordion());
        menuIcerik.setDomain(domain);
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
        List<Menu> menus = menuGroupId != null ? menuRepository.findAllByGroupIdAndDomain_Id(menuGroupId, domain.getId()) : new ArrayList<>();
        List<MenuIcerik> menuIceriks = new ArrayList<>();

        Long maxGroupId = menuIcerikRepository.findMaxGroupId().orElse(0L);
        for (int i = 0; i < menuIcerikDTOS.size(); i++) {
            MenuIcerikDTO dto = menuIcerikDTOS.get(i);
            Menu menu = menus.size() > i ? menus.get(i) : null;

            GenelDilCategory dilCategory = null;
            if (dto.getGenelDilCategoryId() != null) {
                dilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                if (dilCategory == null) {
                    return new ApiResponse<>(false, "Dil seçimi bulunamadı.", null);
                }
            }
            MenuIcerik menuIcerik = new MenuIcerik();
            menuIcerik.setBaslik(dto.getBaslik());
            menuIcerik.setIcerik(dto.getIcerik() != null ? dto.getIcerik().getBytes() : null);
            menuIcerik.setDeleted(dto.getDeleted());
            menuIcerik.setMenuGroupId(menu !=null ? menu.getGroupId() :null);
            menuIcerik.setDomain(domain);
            menuIcerik.setGroupId(maxGroupId+1);
            menuIcerik.setMenu(menu);
            menuIcerik.setGenelDilCategory(dilCategory);
            menuIceriks.add(menuIcerik);
        }
        if (menuIceriks.isEmpty()) {
            return new ApiResponse<>(false, "Kaydedilecek içerik bulunamadı.", null);
        }
        menuIcerikRepository.saveAll(menuIceriks);
        return new ApiResponse<>(true, "Kayıt başarılı.", null);
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
            dto.setMenuGroupId(menuIcerik.getMenu() !=null ? menuIcerik.getMenuGroupId():null);
            dto.setMenuId(menuIcerik.getMenu() != null ? menuIcerik.getMenu().getId() : null);
            dto.setIcerik(menuIcerik.getIcerik() != null ? new String(menuIcerik.getIcerik(), StandardCharsets.UTF_8) : null);
            dto.setGenelDilCategoryId(menuIcerik.getGenelDilCategory()!=null? menuIcerik.getGenelDilCategory().getId():null);
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
    public void deleteById(Long groupId) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain==null){
            throw new RuntimeException("Domain bulunamadi.");
        }
        List<MenuIcerik> menuIceriks = menuIcerikRepository.findAllByGroupIdAndDomain_Id(groupId, domain.getId());
        if (menuIceriks.isEmpty()) {
            throw new RuntimeException("İçerik grubu bulunamadi.");
        }
        menuIcerikRepository.deleteAll(menuIceriks);
    }
    public ApiResponse getIcerikByAltMenuUrl(String altMenuUrl) {
        //  return new ApiResponse(true, MessageConstant.SUCCESS,getRepository().findOneByAltMenuAnaMenuDomainIdAndAltMenuUrl(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),altMenuUrl).toDTO());
        return null;
    }

}
