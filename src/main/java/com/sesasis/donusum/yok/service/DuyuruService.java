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

import javax.transaction.Transactional;
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

        Duyuru duyuru = this.modelMapperServiceImpl.request().map(duyuruDTO, Duyuru.class);
        duyuru.setNewDomain(newDomain);

        // En yüksek siraNo değerini al ve bir artır
        Long maxSiraNo = duyuruRepository.findMaxSiraNo().orElse(0L);
        duyuru.setSiraNo(maxSiraNo + 1);

        // Tarih ekleme
        duyuru.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDate());

        this.duyuruRepository.save(duyuru);

        DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);
        return new ApiResponse(true, "Kayıt işlemi başarılı.", dto);
    }

    @Transactional
    @Override
    public ApiResponse findAll() {
        List<Duyuru> duyuruList = this.duyuruRepository.findAllByOrderByCreatedAtDesc();

        if (duyuruList.isEmpty()) {
            return new ApiResponse(false, "Liste boş.", null);
        }
        try {
            List<DuyuruDTO> dtos = duyuruList.stream()
                    .map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class))
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, "İşlem başarılı.", dtos);
        } catch (Exception e) {
            // Mapping veya veri çekme hatalarını döndür
            return new ApiResponse(false, "Veri çekme veya mapping sırasında hata oluştu: " + e.getMessage(), null);
        }
    }

    @Transactional
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
    public ApiResponse DomainEkle(Long newDomainId, Long duyuruId) {
        NewDomain newDomain = newDomainsRepository.findById(newDomainId)
                .orElseThrow(()-> new RuntimeException("Domain bulunamadı : "+newDomainId));
        Duyuru duyuru = duyuruRepository.findById(duyuruId)
                .orElseThrow(()-> new RuntimeException("Duyuru bulunamadı : "+duyuruId));
        duyuru.setNewDomain(newDomain);
        this.duyuruRepository.save(duyuru);
        return new ApiResponse<>(true,"Domain ekleme işlemi başarılı.",duyuru);
    }
    public ApiResponse siraNoGuncelle() {
        List<Duyuru> duyuruList = this.duyuruRepository.findAllByOrderByCreatedAtDesc();

        if (duyuruList.isEmpty()) {
            return new ApiResponse(false, "Sıra güncellemesi yapılacak duyuru bulunamadı.", null);
        }

        long index = 1;
        for (Duyuru duyuru : duyuruList) {
            duyuru.setSiraNo(index++);
        }

        duyuruRepository.saveAll(duyuruList);
        return new ApiResponse<>(true, "Sıra güncellendi.", null);
    }

}