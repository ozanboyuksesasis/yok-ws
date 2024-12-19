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
        if (altMenuDTO.getUrl()!=null){
            boolean exists = altMenuRepository.existsByUrlAndDomain_Id(altMenuDTO.getUrl(), domain.getId());
            if (exists){
                return new ApiResponse<>(false,"Bu url daha önce kullanılmıştır.",altMenuDTO.getUrl());
            }
        }
        AltMenu altMenu = modelMapperService.request().map(altMenuDTO, AltMenu.class);
        altMenu.setMenu(menu);

        altMenuRepository.save(altMenu);
        return new ApiResponse(true, "Alt Menü başarıyla kaydedildi: ", null);
    }

    public ApiResponse addListAltMenu(List<AltMenuDTO> altMenuDTOList, Long menuGroupId) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }

        Long count = altMenuRepository.findMaxGroupId().orElse(0L);
        List<AltMenu> altMenus = new ArrayList<>();

        List<Menu> menus = menuRepository.findAllByGroupIdAndDomain_Id(menuGroupId, domain.getId());
        if (menus == null || menus.isEmpty()) {
            return new ApiResponse<>(false, "Menü bulunamadı.", null);
        }

        for (int i = 0; i < altMenuDTOList.size() && i < menus.size(); i++) {
            AltMenuDTO altMenuDTO = altMenuDTOList.get(i);
            Menu menu = menus.get(i);
            if (altMenuDTO.getUrl()!=null){
                boolean exists = altMenuRepository.existsByUrlAndDomain_Id(altMenuDTO.getUrl(), domain.getId());
                if (exists){
                    return new ApiResponse<>(false,"Bu url daha önce kullanılmıştır.",altMenuDTO.getUrl());
                }
            }
            AltMenu altMenu = new AltMenu();
            altMenu.setAd(altMenuDTO.getAd());
            altMenu.setUrl(altMenuDTO.getUrl());
            altMenu.setDeleted(altMenuDTO.getDeleted());
            altMenu.setGroupId(count + 1);
            altMenu.setDomain(domain);
            altMenu.setMenuGroupId(menu.getGroupId());
            altMenu.setMenu(menu);
            if (altMenuDTO.getGenelDilCategoryId() != null) {
                GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(altMenuDTO.getGenelDilCategoryId()).orElse(null);
                if (genelDilCategory == null) {
                    return new ApiResponse<>(false, "Dil kategorisi bulunamadı.", null);
                }
                altMenu.setGenelDilCategory(genelDilCategory);
            }
            altMenus.add(altMenu);
        }
        altMenuRepository.saveAll(altMenus);
        return new ApiResponse<>(true, "Alt menü kayıt başarılı.", null);
    }


    @Override
    public ApiResponse findAll() {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<AltMenu> altMenus = altMenuRepository.findAllByMenuDomainId(domain.getId());
        List<AltMenuDTO> dtos = altMenus.stream().map(altMenu -> {
            AltMenuDTO dto = new AltMenuDTO();
            dto.setAd(altMenu.getAd());
            dto.setId(altMenu.getId());
            dto.setMenuId(altMenu.getMenu().getId());
            dto.setDeleted(altMenu.getDeleted());
            dto.setMenuGroupId(altMenu.getMenu().getGroupId());
            dto.setGenelDilCategoryId(altMenu.getGenelDilCategory().getId());
            dto.setGroupId(altMenu.getGroupId());
            dto.setUrl(altMenu.getUrl());
            return dto;
        }).collect(Collectors.toList());

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
