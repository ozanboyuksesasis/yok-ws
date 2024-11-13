package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DomainLogoDTO;
import com.sesasis.donusum.yok.entity.DomainLogo;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainLogoRepository;
import com.sesasis.donusum.yok.repository.FotografRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainLogoService implements IService<DomainLogoDTO> {
    private final DomainLogoRepository domainLogoRepository;
    private ModelMapperServiceImpl modelMapperServiceImpl;
    private final FotografRepository fotografRepository;

    @Override
    public ApiResponse save(DomainLogoDTO domainLogoDTO) {
        return null;
    }

    @Override
    public ApiResponse findAll() {
        List<DomainLogo> domainLogos = domainLogoRepository.findAll();
        if (domainLogos.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş.", null);
        }
        List<DomainLogoDTO> dtos = domainLogos.stream().map(domainLogo ->
                this.modelMapperServiceImpl.response().map(domainLogo, DomainLogoDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", null);
    }

    @Override
    public ApiResponse findById(Long id) {

        try {
            DomainLogo domainLogo = domainLogoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Domain logo bulunamadı."));
            DomainLogoDTO dto = this.modelMapperServiceImpl.response().map(domainLogo, DomainLogoDTO.class);
            return new ApiResponse<>(true, "İşlem başarılı.", dto);
        } catch (RuntimeException exception) {
            return new ApiResponse<>(false, exception.getMessage(), null);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (domainLogoRepository.existsById(id)) {
            domainLogoRepository.deleteById(id);
        }
    }
}
