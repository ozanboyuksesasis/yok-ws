package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.*;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.entity.NewMenu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Transactional
public class NewDomainService implements IService<NewDomainDTO> {
    private final RoleRepository roleRepository;
    private final NewDomainsRepository newDomainsRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;


    @Override
    public ApiResponse save(NewDomainDTO newDomainDTO) {
        try {
            Role role = roleRepository.findById(newDomainDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            NewDomain newDomain = modelMapperServiceImpl.request().map(newDomainDTO, NewDomain.class);
            newDomain.setRole(role);
            newDomainsRepository.save(newDomain);

            NewDomainDTO dto = modelMapperServiceImpl.response().map(newDomain, NewDomainDTO.class);
            return new ApiResponse<>(true, "Domain kayıt işlemi başarılı.", dto);

        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, "Role bulunamadı. Lütfen doğru bilgileri girin.", null);
        }
    }

    @Override
    public ApiResponse findAll() {
        List<NewDomain> domains = newDomainsRepository.findAll();
        if (domains.isEmpty()) {
            return new ApiResponse(false, "Liste boş.", null);
        }

        List<NewDomainDTO> dtos = domains.stream().map(domain -> {
            NewDomainDTO dto = new NewDomainDTO();
            dto.setId(domain.getId());
            dto.setIsim(domain.getIsim());
            dto.setUrl(domain.getUrl());
            dto.setAnaDomainMi(domain.isAnaDomainMi());
            dto.setRoleId(domain.getRole().getId());

            dto.setMenuList(domain.getMenuList().stream().map(menu -> modelMapperServiceImpl.response().map( menu, NewMenuDTO.class)).collect(Collectors.toList()));
            dto.setHaberDTOS(domain.getHaberList().stream().map(haber -> modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));
            dto.setDuyuruDTOS(domain.getDuyuruList().stream().map(duyuru -> modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList()));
            dto.setSliders(domain.getSliders().stream().map(slider -> modelMapperServiceImpl.response().map(slider, SliderDTO.class)).collect(Collectors.toList()));
            dto.setDomainLogos(domain.getDomainLogos().stream().map(logo -> modelMapperServiceImpl.response().map(logo, DomainLogoDTO.class)).collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }


    @Override
    public ApiResponse findById(Long id) {
        NewDomainDTO dto;
        try {
            NewDomain domain = newDomainsRepository.findById(id).orElse(null);
            if (domain == null) {
                return new ApiResponse<>(false,"Domain bulunamadı.",null);
            }

            dto = this.modelMapperServiceImpl.response().map(domain, NewDomainDTO.class);
            dto.setRoleId(domain.getRole().getId());
            dto.setMenuList(domain.getMenuList().stream().map(newMenu -> this.modelMapperServiceImpl.response().map(newMenu, NewMenuDTO.class)).collect(Collectors.toList()));
            dto.setHaberDTOS(domain.getHaberList().stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));
            dto.setDuyuruDTOS(domain.getDuyuruList().stream().map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList()));
            dto.setSliders(domain.getSliders().stream().map(slider -> this.modelMapperServiceImpl.response().map(slider, SliderDTO.class)).collect(Collectors.toList()));
            dto.setDomainLogos(domain.getDomainLogos().stream().map(domainLogo -> this.modelMapperServiceImpl.response().map(domainLogo, DomainLogoDTO.class)).collect(Collectors.toList()));
            return new ApiResponse<>(true,"İşlem başarılı.",dto);
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }

    }

    @Override
    public void deleteById(Long id) {
        if (newDomainsRepository.existsById(id)) {
            newDomainsRepository.deleteById(id);
        }
    }
}
