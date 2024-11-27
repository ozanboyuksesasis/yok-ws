package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainRepository;
import com.sesasis.donusum.yok.repository.DuyuruRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DuyuruService implements IService<DuyuruDTO> {

    private final DuyuruRepository duyuruRepository;
    private final DomainRepository domainRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final GenelDilCategoryRepository genelDilCategoryRepository;
    private final SecurityContextUtil securityContextUtil;

    @Override
    public ApiResponse save(DuyuruDTO duyuruDTO) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        GenelDilCategory dilCategory = this.genelDilCategoryRepository.findById(duyuruDTO.getGenelDilCategoryId()).orElse(null);
        if (dilCategory == null) {
            return new ApiResponse(false, "Dil seçimi bulunamadı.", null);
        }
        Duyuru duyuru = null;
        if (duyuruDTO.getId() != null) {
            duyuru = duyuruRepository.findById(duyuruDTO.getId()).orElse(null);
           if (duyuru != null){
               duyuru.setDomain(domain);
               duyuru.setGenelDilCategory(dilCategory);
               duyuru.setBaslik(duyuruDTO.getBaslik());
               duyuru.setAltBaslik(duyuruDTO.getAltBaslik());
               duyuru.setDuyuruIcerik(duyuruDTO.getDuyuruIcerik());
               duyuru.setAktifMi(duyuruDTO.getAktifMi());
               duyuru.setUpdateAt(LocalDate.now());
           }
        }else {

            duyuru = this.modelMapperServiceImpl.request().map(duyuruDTO, Duyuru.class);
            duyuru.setDomain(domain);
            Long maxSiraNo = duyuruRepository.findMaxSiraNo().orElse(0L);
            duyuru.setSiraNo(maxSiraNo + 1);
            duyuru.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDate());
            duyuru.setGenelDilCategory(dilCategory);
            this.duyuruRepository.save(duyuru);

        }

        DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);
        return new ApiResponse(true, "Kayıt işlemi başarılı.", dto);
    }

    @Transactional
    @Override
    public ApiResponse findAll() {
        List<Duyuru> duyuruList = this.duyuruRepository.findAllByOrderBySiraNoDesc();

        if (duyuruList.isEmpty()) {
            return new ApiResponse(false, "Liste boş.", null);
        }
        try {
            List<DuyuruDTO> dtos = duyuruList.stream()
                    .map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class))
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, "İşlem başarılı.", dtos);
        } catch (Exception e) {
            return new ApiResponse(false, "Veri çekme veya mapping sırasında hata oluştu: " + e.getMessage(), null);
        }
    }

    @Transactional
    @Override
    public ApiResponse findById(Long id) {
        Duyuru duyuru = this.duyuruRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Duyuru bulunamadı."));
        DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);

        return new ApiResponse<>(true, "İşlem başarılı.", dto);
    }

    @Override
    public void deleteById(Long id) {
        if (this.duyuruRepository.existsById(id)) {
            this.duyuruRepository.deleteById(id);
            siraNoGuncelle();
        }
    }

    @Transactional
    public ApiResponse DomainEkle(Long newDomainId, Long duyuruId) {
        Domain newDomain = domainRepository.findById(newDomainId)
                .orElseThrow(() -> new RuntimeException("Domain bulunamadı : " + newDomainId));
        Duyuru duyuru = duyuruRepository.findById(duyuruId)
                .orElseThrow(() -> new RuntimeException("Duyuru bulunamadı : " + duyuruId));
        duyuru.setDomain(newDomain);
        this.duyuruRepository.save(duyuru);
        return new ApiResponse<>(true, "Domain ekleme işlemi başarılı.", duyuru);
    }

    @Transactional
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

    public ApiResponse duyuruListTrueDomainId(Long domainId, Long dilCategoryId) {
        List<Duyuru> duyuruList = duyuruRepository.findByDomain_IdAndGenelDilCategory_IdAndAktifMiTrueOrderBySiraNoDesc(domainId, dilCategoryId);

        if (duyuruList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<DuyuruDTO> duyurus = duyuruList.stream().map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", duyurus);
    }

    public ApiResponse duyuruListFalseDomainId(Long domainId, Long dilCategoryId) {
        List<Duyuru> duyuruList = duyuruRepository.findByDomain_IdAndGenelDilCategory_IdAndAktifMiFalseOrderBySiraNoDesc(domainId, dilCategoryId);

        if (duyuruList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<DuyuruDTO> duyurus = duyuruList.stream().map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", duyurus);
    }

}
