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

@Service
@RequiredArgsConstructor
public class NewDomainService implements IService<NewDomainDTO> {
    private final RoleRepository roleRepository;
    private final NewDomainsRepository newDomainsRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;


    @Override
    public ApiResponse save(NewDomainDTO newDomainDTO) {
        Role role = roleRepository.findById(newDomainDTO.getRoleId()).
                orElseThrow(() -> new RuntimeException("Role not found"));
        NewDomain newDomain = this.modelMapperServiceImpl.request().map(newDomainDTO, NewDomain.class);
        newDomain.setRole(role);
        newDomainsRepository.save(newDomain);
        NewDomainDTO DTO = modelMapperServiceImpl.response().map(newDomain, NewDomainDTO.class);
        return new ApiResponse<>(true,"Domain kayıt işlemi başarılı.",DTO);
    }

    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
