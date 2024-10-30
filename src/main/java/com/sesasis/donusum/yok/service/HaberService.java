package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.HaberRepository;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HaberService implements IService<HaberDTO> {

    private final HaberRepository haberRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final NewDomainsRepository newDomainsRepository;
    @Override
    public ApiResponse save(HaberDTO haberDTO) {

        NewDomain newDomain = null;
        if (haberDTO.getNewDomainId() != null) {
            newDomain = this.newDomainsRepository.findById(haberDTO.getNewDomainId()).orElse(null);
        }

        Haber haber = this.modelMapperServiceImpl.request().map(haberDTO, Haber.class);
        haber.setNewDomain(newDomain);
        Long maxSiraNo = haberRepository.findMaxSiraNo().orElse(0L);
        haber.setSiraNo(maxSiraNo + 1);

        haber.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDate());

        this.haberRepository.save(haber);

        HaberDTO dto = this.modelMapperServiceImpl.response().map(haber, HaberDTO.class);
        return new ApiResponse(true, "Kayıt işlemi başarılı.", dto);
    }

    @Override
    public ApiResponse findAll() {
       List<Haber> habers = this.haberRepository.findAll();
       if (habers.isEmpty()) {
           return new ApiResponse<>(false,"Liste boş.",null);
       }

        List<HaberDTO> dtos = habers.stream().map(haber ->
                this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        Haber haber = this.haberRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("Haber bulunamadı."));
        HaberDTO dto = this.modelMapperServiceImpl.response().map(haber, HaberDTO.class);
        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
    if (this.haberRepository.existsById(id)) {
        this.haberRepository.deleteById(id);
    }
    }
    public ApiResponse DomainEkle(Long newDomainId, Long duyuruId) {
        NewDomain newDomain = newDomainsRepository.findById(newDomainId)
                .orElseThrow(()-> new RuntimeException("Domain bulunamadı : "+newDomainId));
        Haber haber = haberRepository.findById(duyuruId)
                .orElseThrow(()-> new RuntimeException("Duyuru bulunamadı : "+duyuruId));
        haber.setNewDomain(newDomain);
        this.haberRepository.save(haber);
        return new ApiResponse<>(true,"Domain ekleme işlemi başarılı.",null);
    }
    public ApiResponse siraNoGuncelle() {
        List<Haber> habers = this.haberRepository.findAllByOrderByCreatedAtDesc();

        if (habers.isEmpty()) {
            return new ApiResponse(false, "Sıra güncellemesi yapılacak duyuru bulunamadı.", null);
        }
        long index = 1;
        for (Haber haber : habers) {
            haber.setSiraNo(index++);
        }

        haberRepository.saveAll(habers);
        return new ApiResponse<>(true, "Sıra güncellendi.", null);
    }



}
