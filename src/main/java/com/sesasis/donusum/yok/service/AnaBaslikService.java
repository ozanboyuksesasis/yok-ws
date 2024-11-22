package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;

import com.sesasis.donusum.yok.dto.AnaBaslikDTO;
import com.sesasis.donusum.yok.entity.AnaBaslik;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AnaBaslikRepository;
import com.sesasis.donusum.yok.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnaBaslikService implements IService<AnaBaslikDTO> {

        private final AnaBaslikRepository anaBaslikRepository;
        private final ModelMapperServiceImpl modelMapperServiceImpl;
        private final SecurityContextUtil securityContextUtil;
        private final DomainRepository domainRepository;

    @Override
    public ApiResponse save(AnaBaslikDTO anaBaslikDTO) {
        anaBaslikDTO.setBaslik(anaBaslikDTO.getBaslik().trim());

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        AnaBaslik anaBaslik = this.modelMapperServiceImpl.request().map(anaBaslikDTO, AnaBaslik.class);
        domain.setAnaBaslik(anaBaslik);
        anaBaslik.setDomain(domain);

        domainRepository.save(domain);
        AnaBaslikDTO dto = this.modelMapperServiceImpl.response().map(anaBaslik, AnaBaslikDTO.class);
        return new ApiResponse<>(true,"İşlem Bşarılı.",dto);
    }

    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        AnaBaslik anaBaslik = anaBaslikRepository.findById(id).orElse(null);
        if (anaBaslik==null){
            return new ApiResponse<>(false,"Hata : Başlık bulunamadı.",null);
        }
        AnaBaslikDTO dto = this.modelMapperServiceImpl.response().map(anaBaslik, AnaBaslikDTO.class);
        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
        if (anaBaslikRepository.existsById(id)){
            anaBaslikRepository.deleteById(id);
        }

    }
}
