package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DuyuruRepository;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuyuruService implements IService<DuyuruDTO> {

    private final DuyuruRepository duyuruRepository;
    private final NewDomainsRepository newDomainsRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;


    @Override
    public ApiResponse save(DuyuruDTO duyuruDTO) {

        NewDomain newDomain = null;
        if (duyuruDTO.getNewDomainId() != null) {
            newDomain = this.newDomainsRepository.findById(duyuruDTO.getNewDomainId()).orElse(null);
        }
        Long maxSiraNo = duyuruRepository.findMaxSiraNo().orElse(0L);
        Long yeniSiraNo = maxSiraNo + 1;

        Duyuru duyuru = this.modelMapperServiceImpl.request().map(duyuruDTO, Duyuru.class);
        duyuru.setNewDomain(newDomain);
        duyuru.setSiraNo(yeniSiraNo);
        duyuru.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDate());
        this.duyuruRepository.save(duyuru);

        DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);
        return new ApiResponse(true, "Kayıt işlemi başarılı.", dto);
    }

    @Override
    public ApiResponse findAll() {
        List<Duyuru> duyuruList = this.duyuruRepository.findAllByOrderByCreatedAtDesc();
        if (duyuruList.isEmpty()) {
            return new ApiResponse(false, "Liste boş.", null);
        }
        List<DuyuruDTO> dtos = duyuruList.stream().map(duyuru ->
                this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
       Duyuru duyuru = this.duyuruRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Duyuru bulunamadı."));

       DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);

        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public void deleteById(Long id) {
        if (this.duyuruRepository.existsById(id)) {
            this.duyuruRepository.deleteById(id);
            siraNoGuncelle();
        }
    }
    public ApiResponse DomainEkle(DuyuruDTO duyuruDTO) {
        NewDomain newDomain = newDomainsRepository.findById(duyuruDTO.getNewDomainId())
                .orElseThrow(()-> new RuntimeException("Domain bulunamadı : "+duyuruDTO.getNewDomainId()));
        Duyuru duyuru = duyuruRepository.findById(duyuruDTO.getId())
                .orElseThrow(()-> new RuntimeException("Duyuru bulunamadı : "+duyuruDTO.getId()));
        duyuru.setNewDomain(newDomain);
        this.duyuruRepository.save(duyuru);
        return new ApiResponse<>(true,"Domain ekleme işlemi başarılı.",duyuru);
    }
    public ApiResponse siraNoGuncelle() {
        List<Duyuru> duyuruList = this.duyuruRepository.findAllByOrderByCreatedAtDesc();
        long index = 1;
        for (int i = 0; i < duyuruList.size(); i++) {
            Duyuru duyuru = duyuruList.get(i);
            duyuru.setSiraNo(index++);
        }
        duyuruRepository.saveAll(duyuruList);
        return new ApiResponse<>(true,"Sıra güncellendi.",null);
    }
}
