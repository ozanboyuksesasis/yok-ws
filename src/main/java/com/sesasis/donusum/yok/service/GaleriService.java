package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.GaleriDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Galeri;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GaleriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GaleriService implements IService<GaleriDTO> {
    private final ModelMapperServiceImpl modelMapperService;
    private final GaleriRepository galeriRepository;
    private final SecurityContextUtil securityContextUtil;


    @Override
    public ApiResponse save(GaleriDTO galeriDTO) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        galeriDTO.setName(galeriDTO.getName().trim().toUpperCase());
        Galeri galeri = this.modelMapperService.request().map(galeriDTO,Galeri.class);
        galeri.setDomain(loggedDomain);
        galeriRepository.save(galeri);
        return new ApiResponse<>(true,"Kayıt bşarılı.",null);
    }
    @Override
    public ApiResponse findAll() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "Domain bulunamadı.", null);
        }
        List<Galeri> galeris = galeriRepository.findAllByDomain_Id(loggedDomain.getId());
        List<GaleriDTO> dtos = galeris.stream().map(galeri -> this.modelMapperService.response().map(galeri,GaleriDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }
    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (galeriRepository.existsById(id)){
            galeriRepository.deleteById(id);
        }
    }
}
