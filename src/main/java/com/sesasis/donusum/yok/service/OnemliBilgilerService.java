package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.OnemliBilgilerDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.entity.OnemliBilgiler;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.OnemliBilgilerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OnemliBilgilerService implements IService<OnemliBilgilerDTO> {
    private final OnemliBilgilerRepository onemliBilgilerRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final SecurityContextUtil securityContextUtil;
    private final GenelDilCategoryRepository genelDilCategoryRepository;

    @Override
    public ApiResponse save(OnemliBilgilerDTO onemliBilgilerDTO) {
        onemliBilgilerDTO.setBaslik(onemliBilgilerDTO.getBaslik().trim().toUpperCase());

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        GenelDilCategory dilCategory = genelDilCategoryRepository
                .findById(onemliBilgilerDTO.getGenelDilCategoryId()).orElse(null);
        if (dilCategory == null) {
            return new ApiResponse<>(false, "Dil kategorisi bulunamadı.", null);
        }
        OnemliBilgiler onemliBilgiler = null;
        if (onemliBilgilerDTO.getId() != null) {
            onemliBilgiler = onemliBilgilerRepository.findById(onemliBilgilerDTO.getId()).orElse(null);

            if (onemliBilgiler != null) {
                onemliBilgiler.setAktifMi(onemliBilgilerDTO.getAktifMi());
                onemliBilgiler.setBaslik(onemliBilgilerDTO.getBaslik());
                onemliBilgiler.setAltBaslik(onemliBilgilerDTO.getAltBaslik());
                onemliBilgiler.setOnemliBilgilerIcerik(onemliBilgilerDTO.getOnemliBilgilerIcerik());
                onemliBilgiler.setGenelDilCategory(dilCategory);
                onemliBilgiler.setDomain(domain);
               onemliBilgiler.setUpdateAt(LocalDate.now());
            }
        }else {

            onemliBilgiler = this.modelMapperService.request().map(onemliBilgilerDTO, OnemliBilgiler.class);
            onemliBilgiler.setOnemliBilgilerIcerik(onemliBilgilerDTO.getOnemliBilgilerIcerik());
            onemliBilgiler.setDomain(domain);
            onemliBilgiler.setGenelDilCategory(dilCategory);
            onemliBilgiler.setAktifMi(onemliBilgilerDTO.getAktifMi());
            Long maxSiraNo = onemliBilgilerRepository.findMaxSiraNo().orElse(0L);
            onemliBilgiler.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDate());
            onemliBilgiler.setSiraNo(maxSiraNo + 1);
            onemliBilgilerRepository.save(onemliBilgiler);
        }
        OnemliBilgilerDTO dto = this.modelMapperService.response().map(onemliBilgiler, OnemliBilgilerDTO.class);


        return new ApiResponse<>(true, "İşlem başarılı.", dto);
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
        if (onemliBilgilerRepository.existsById(id)) {
            onemliBilgilerRepository.deleteById(id);
        }
    }

    public ApiResponse onemliBilgilerListTrueTrueDomainId(Long domainId, Long dilCategoryId) {
        List<OnemliBilgiler> onemliBilgilers = onemliBilgilerRepository
                .findByDomain_IdAndGenelDilCategory_IdAndAktifMiTrueOrderBySiraNoDesc(domainId, dilCategoryId);

        if (onemliBilgilers.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<OnemliBilgilerDTO> onemliBilgilerDTOS = onemliBilgilers.stream()
                .map(onemliBilgiler -> {
                    OnemliBilgilerDTO dto = new OnemliBilgilerDTO();
                    dto.setId(onemliBilgiler.getId());
                    dto.setSiraNo(onemliBilgiler.getSiraNo());
                    dto.setGenelDilCategoryId(onemliBilgiler.getGenelDilCategory().getId());
                    dto.setBaslik(onemliBilgiler.getBaslik());
                    dto.setAltBaslik(onemliBilgiler.getAltBaslik());
                    dto.setOnemliBilgilerIcerik(onemliBilgiler.getOnemliBilgilerIcerik());
                    dto.setSayfaUrl(onemliBilgiler.getSayfaUrl());
                    dto.setCreatedAt(onemliBilgiler.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", onemliBilgilerDTOS);
    }

    public ApiResponse onemliBilgilerFalseTrueDomainId(Long domainId, Long dilCategoryId) {
        List<OnemliBilgiler> onemliBilgilers = onemliBilgilerRepository
                .findByDomain_IdAndGenelDilCategory_IdAndAktifMiFalseOrderBySiraNoDesc(domainId, dilCategoryId);

        if (onemliBilgilers.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş", null);
        }

        List<OnemliBilgilerDTO> onemliBilgilerDTOS = onemliBilgilers.stream()
                .map(onemliBilgiler -> {
                    OnemliBilgilerDTO dto = new OnemliBilgilerDTO();
                    dto.setId(onemliBilgiler.getId());
                    dto.setSiraNo(onemliBilgiler.getSiraNo());
                    dto.setGenelDilCategoryId(onemliBilgiler.getGenelDilCategory().getId());
                    dto.setBaslik(onemliBilgiler.getBaslik());
                    dto.setAltBaslik(onemliBilgiler.getAltBaslik());
                    dto.setOnemliBilgilerIcerik(onemliBilgiler.getOnemliBilgilerIcerik());
                    dto.setSayfaUrl(onemliBilgiler.getSayfaUrl());
                    dto.setCreatedAt(onemliBilgiler.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", onemliBilgilerDTOS);
    }

}
