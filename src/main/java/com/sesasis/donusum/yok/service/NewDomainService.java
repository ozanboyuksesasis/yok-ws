package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.NewDomainDTO;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewDomainService implements IService<NewDomainDTO> {
    private final RoleRepository roleRepository;
    private final NewDomainsRepository newDomainsRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;


    @Override
    public ApiResponse save(NewDomainDTO newDomainDTO) {
        Role role = roleRepository.findById(newDomainDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        NewDomain newDomain = modelMapperServiceImpl.request().map(newDomainDTO, NewDomain.class);
        newDomain.setRole(role);
        newDomainsRepository.save(newDomain);

        NewDomainDTO dto = modelMapperServiceImpl.response().map(newDomain, NewDomainDTO.class);
        return new ApiResponse<>(true, "Domain kayıt işlemi başarılı.", dto);
    }


    @Override
    public ApiResponse findAll() {

        List<NewDomain> domains = newDomainsRepository.findAll();
        if (domains.isEmpty()) {
            return new ApiResponse(false,"Liste boş.",null);
        }
        List<NewDomainDTO> dtos = domains.stream().map(
                domain -> this.modelMapperServiceImpl.response().map(domain,NewDomainDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        NewDomain domain = newDomainsRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Domain not found"));
        NewDomainDTO dto = this.modelMapperServiceImpl.response().map(domain,NewDomainDTO.class);

        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
    if (newDomainsRepository.existsById(id)) {
        newDomainsRepository.deleteById(id);
    }
    }
}
