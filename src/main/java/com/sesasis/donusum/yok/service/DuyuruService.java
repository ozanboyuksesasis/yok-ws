package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainRepository;
import com.sesasis.donusum.yok.repository.DuyuruRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.HaberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
    private final HaberRepository haberRepository;

    public ApiResponse listSave(List<DuyuruDTO> duyuruDTOList) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();

        List<GenelDilCategory> dilCategoryList = this.genelDilCategoryRepository.findAll();
        if (dilCategoryList == null) {
            return new ApiResponse(false, "Dil Listesi bulunamadı.", null);
        }
        Long count = duyuruRepository.findMaxSiraNo().orElse(0L);
        AtomicLong atomicLong = new AtomicLong(count + 1);
        List<Duyuru> duyuruList = new ArrayList<>();
        for (DuyuruDTO duyuruDTO : duyuruDTOList) {

            Duyuru duyuru = new Duyuru();
            duyuru.setDomain(domain);
            duyuru.setAktifMi(duyuruDTO.getAktifMi());
            duyuru.setIcerik(duyuruDTO.getIcerik());
            duyuru.setBaslik(duyuruDTO.getBaslik());
            duyuru.setSayfaUrl(duyuruDTO.getSayfaUrl());
            duyuru.setCreatedAt(LocalDate.now());
            duyuru.setAltBaslik(duyuruDTO.getAltBaslik());
            duyuru.setSiraNo(atomicLong.getAndIncrement());
            duyuru.setEventDate(duyuruDTO.getEventDate());

            GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(duyuruDTO.getGenelDilCategoryId()).orElse(null);
            if (genelDilCategory != null) {
                duyuru.setGenelDilCategory(genelDilCategory);
            } else {
                return new ApiResponse<>(false, "Dil seçimi bulunamadı.", null);

            }
            duyuruList.add(duyuru);
        }
        duyuruRepository.saveAll(duyuruList);


        return new ApiResponse<>(true, "Kayıt başarılı.", null);

    }


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
            if (duyuru != null) {
                duyuru.setDomain(domain);
                duyuru.setGenelDilCategory(dilCategory);
                duyuru.setBaslik(duyuruDTO.getBaslik());
                duyuru.setAltBaslik(duyuruDTO.getAltBaslik());
                duyuru.setIcerik(duyuruDTO.getIcerik());
                duyuru.setAktifMi(duyuruDTO.getAktifMi());
                duyuru.setUpdateAt(LocalDate.now());
                duyuru.setEventDate(duyuruDTO.getEventDate());
            }
        }else {
            return new ApiResponse<>(false,"Tek kayıt oluşturalamaz.Güncelleme yapılabilir.",null);
        }

        DuyuruDTO dto = this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class);
        return new ApiResponse(true, "Güncelleme işlemi başarılı.", dto);
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
    public ApiResponse getDuyuruByDomainId(Long domainId) {
        List<Duyuru> duyurus = duyuruRepository.findAllByDomainId(domainId);
        if (duyurus == null) {
            return new ApiResponse<>(false, "Duyuru listesi bulunamadı.", null);
        }
        long index = duyurus.size();

        for (Duyuru duyuru : duyurus) {
            duyuru.setSiraNo(index--);
        }

        List<DuyuruDTO> duyuruDTOS = duyurus.stream()
                .map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", duyuruDTOS);
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
