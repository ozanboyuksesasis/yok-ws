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
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.HaberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class HaberService implements IService<HaberDTO> {

    private final HaberRepository haberRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final DomainRepository domainRepository;
    private final GenelDilCategoryRepository genelDilCategoryRepository;
    private final SecurityContextUtil securityContextUtil;
    private final ModelMapper modelMapper;


    public ApiResponse listSave(List<HaberDTO> haberDTOS) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        Long count = haberRepository.findMaxSiraNo().get();
        AtomicLong atomicLong = new AtomicLong(count + 1);

        List<Haber> habers = haberDTOS.stream().map(haberDTO -> {
            Haber haber = new Haber();
            haber.setDomain(domain);
            haber.setBaslik(haberDTO.getBaslik());
            haber.setIcerik(haberDTO.getIcerik());
            haber.setAktifMi(haberDTO.getAktifMi());
            haber.setAltBaslik(haberDTO.getAltBaslik());
            haber.setCreatedAt(LocalDate.now());
            if (haberDTO.getGenelDilCategoryId() != null) {
                GenelDilCategory dilCategory = genelDilCategoryRepository.findById(haberDTO.getGenelDilCategoryId()).orElse(null);
                if (dilCategory != null) {
                    haber.setGenelDilCategory(dilCategory);
                }

            }

            haber.setSayfaUrl(haberDTO.getSayfaUrl());
            haber.setSiraNo(atomicLong.getAndIncrement());
            return haber;
        }).collect(Collectors.toList());

        haberRepository.saveAll(habers);

        return new ApiResponse<>(true, "Kayıt başarılı.", null);

    }


    @Override
    public ApiResponse save(HaberDTO haberDTO) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        GenelDilCategory genelDilCategory = this.genelDilCategoryRepository.findById(haberDTO.getGenelDilCategoryId()).orElse(null);
        if (genelDilCategory == null) {
            return new ApiResponse<>(false, "Dil kategorisi bulunamadı.", null);
        }
        Haber haber = null;
        if (haberDTO.getId() != null) {
            haber = this.haberRepository.findById(haberDTO.getId()).orElse(null);
            if (haber != null) {
                haber.setGenelDilCategory(genelDilCategory);
                haber.setDomain(domain);
                haber.setBaslik(haberDTO.getBaslik());
                haber.setIcerik(haberDTO.getIcerik());
                haber.setAktifMi(haberDTO.getAktifMi());
                haber.setAltBaslik(haberDTO.getAltBaslik());
                haber.setUpdateAt(LocalDate.now());
            }
        } else {
            return new ApiResponse<>(false, "Tek kayıt oluşturalamaz.Güncelleme yapılabilir.", null);
        }

        HaberDTO dto = this.modelMapperServiceImpl.response().map(haber, HaberDTO.class);
        return new ApiResponse(true, "Güncelleme işlemi başarılı.", dto);
    }

    @Override
    public ApiResponse findAll() {
        List<Haber> habers = this.haberRepository.findAllByOrderBySiraNoDesc();
        if (habers.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş.", null);
        }

        List<HaberDTO> dtos = habers.stream().map(haber ->
                this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        Haber haber = this.haberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Haber bulunamadı."));
        HaberDTO dto = this.modelMapperServiceImpl.response().map(haber, HaberDTO.class);
        return new ApiResponse<>(true, "İşlem başarılı.", dto);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (this.haberRepository.existsById(id)) {
            this.haberRepository.deleteById(id);
            siraNoGuncelle();
        }
    }

    public ApiResponse DomainEkle(Long newDomainId, Long duyuruId) {
        Domain newDomain = domainRepository.findById(newDomainId)
                .orElseThrow(() -> new RuntimeException("Domain bulunamadı : " + newDomainId));
        Haber haber = haberRepository.findById(duyuruId)
                .orElseThrow(() -> new RuntimeException("Duyuru bulunamadı : " + duyuruId));
        haber.setDomain(newDomain);
        this.haberRepository.save(haber);
        return new ApiResponse<>(true, "Domain ekleme işlemi başarılı.", null);
    }

    @Transactional
    public ApiResponse siraNoGuncelle() {
        List<Haber> habers = this.haberRepository.findAllByOrderByCreatedAtDesc();

        if (habers.isEmpty()) {
            return new ApiResponse(false, "Sıra güncellemesi yapılacak haber bulunamadı.", null);
        }
        long index = 1;
        for (Haber haber : habers) {
            haber.setSiraNo(index++);
        }

        haberRepository.saveAll(habers);
        return new ApiResponse<>(true, "Sıra güncellendi.", null);
    }

    public ApiResponse getHabersDomainId(Long domainId) {
        List<Haber> habers = haberRepository.findAllByDomainId(domainId);
        if (habers == null) {
            return new ApiResponse<>(false, "Haber listesi bulunamadı.", null);
        }
        long index = habers.size();

        for (Haber haber : habers) {
            haber.setSiraNo(index--);
        }


        List<HaberDTO> haberDTOS = habers.stream()
                .map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", haberDTOS);
    }


    public ApiResponse haberListTrueDomainId(Long domainId, Long dilCategoryId) {
        List<Haber> haberList = haberRepository.findByDomain_IdAndGenelDilCategory_IdAndAktifMiTrueOrderBySiraNoDesc(domainId, dilCategoryId);

        if (haberList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<HaberDTO> haberDTOS = haberList.stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", haberDTOS);
    }

    public ApiResponse haberListFalseDomainId(Long domainId, Long dilCategoryId) {
        List<Haber> haberList = haberRepository.findByDomain_IdAndGenelDilCategory_IdAndAktifMiFalseOrderBySiraNoDesc(domainId, dilCategoryId);

        if (haberList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<HaberDTO> haberDTOS = haberList.stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", haberDTOS);
    }


}
