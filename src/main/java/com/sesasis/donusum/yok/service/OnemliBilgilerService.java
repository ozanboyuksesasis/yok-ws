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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OnemliBilgilerService implements IService<OnemliBilgilerDTO> {
    private final OnemliBilgilerRepository onemliBilgilerRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final SecurityContextUtil securityContextUtil;
    private final GenelDilCategoryRepository genelDilCategoryRepository;


    public ApiResponse listSave(List<OnemliBilgilerDTO> dtoList) {

        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();

        Long count = onemliBilgilerRepository.findMaxSiraNo().orElse(0L);
        AtomicLong atom = new AtomicLong(count + 1);

        List<OnemliBilgiler> onemliBilgilers = new ArrayList<>();

        for (OnemliBilgilerDTO dto : dtoList) {

            OnemliBilgiler onemliBilgiler = new OnemliBilgiler();
            onemliBilgiler.setDomain(domain);
            onemliBilgiler.setIcerik(dto.getIcerik());
            onemliBilgiler.setCreatedAt(LocalDate.now());
            onemliBilgiler.setSiraNo(atom.getAndIncrement());
            onemliBilgiler.setAktifMi(dto.getAktifMi());
            onemliBilgiler.setBaslik(dto.getBaslik());
            onemliBilgiler.setAltBaslik(dto.getAltBaslik());
            onemliBilgiler.setSayfaUrl(dto.getSayfaUrl());
            if (dto.getGenelDilCategoryId() != null) {
                GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(dto.getGenelDilCategoryId()).orElse(null);
                onemliBilgiler.setGenelDilCategory(genelDilCategory);
            }
            onemliBilgilers.add(onemliBilgiler);
        }
        onemliBilgilerRepository.saveAll(onemliBilgilers);

        return new ApiResponse<>(true,"Kayıt başarılı.",null);
    }


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
                onemliBilgiler.setIcerik(onemliBilgilerDTO.getIcerik());
                onemliBilgiler.setGenelDilCategory(dilCategory);
                onemliBilgiler.setDomain(domain);
                onemliBilgiler.setUpdateAt(LocalDate.now());
            }
        } else {
            return new ApiResponse<>(false, "Tek kayıt oluşturalamaz.Güncelleme yapılabilir.", null);

        }
        OnemliBilgilerDTO dto = this.modelMapperServiceImpl.response().map(onemliBilgiler, OnemliBilgilerDTO.class);

        return new ApiResponse(true, "Güncelleme işlemi başarılı.", dto);
    }

    @Override
    public ApiResponse findAll() {
      List<OnemliBilgiler> onemliBilgilers = onemliBilgilerRepository.findAll();
      if (onemliBilgilers.isEmpty()){
          return new ApiResponse<>(false,"Liste boş",null);
      }
      List<OnemliBilgilerDTO> dtos = onemliBilgilers.stream().map(onemliBilgiler -> this.modelMapperServiceImpl.response()
              .map(onemliBilgiler,OnemliBilgilerDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }
    public ApiResponse getOnemliBilgilerByDomainId(Long domainId) {
        List<OnemliBilgiler> onemliBilgilers = onemliBilgilerRepository.findAllByDomainId(domainId);
        if (onemliBilgilers == null) {
            return new ApiResponse<>(false, "Haber listesi bulunamadı.", null);
        }
        long index = onemliBilgilers.size();

        for (OnemliBilgiler onemliBilgiler : onemliBilgilers) {
            onemliBilgiler.setSiraNo(index--);
        }

        List<OnemliBilgilerDTO> onemliBilgilerDTOS = onemliBilgilers.stream()
                .map(onemliBilgiler -> this.modelMapperServiceImpl.response().map(onemliBilgiler, OnemliBilgilerDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", onemliBilgilerDTOS);
    }


    @Override
    public ApiResponse findById(Long onemliBilgiId) {
        OnemliBilgiler onemliBilgi = onemliBilgilerRepository.findById(onemliBilgiId).orElse(null);
        if (onemliBilgi == null) {
            return new ApiResponse(false, "Önemli bilgi bulunamadı.", null);
        }
        OnemliBilgilerDTO dto = modelMapperServiceImpl.response().map(onemliBilgi, OnemliBilgilerDTO.class);
        return new ApiResponse(true, "İşlem başarılı.", dto);
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
                    dto.setAktifMi(onemliBilgiler.getAktifMi());
                    dto.setSiraNo(onemliBilgiler.getSiraNo());
                    dto.setGenelDilCategoryId(onemliBilgiler.getGenelDilCategory().getId());
                    dto.setBaslik(onemliBilgiler.getBaslik());
                    dto.setAltBaslik(onemliBilgiler.getAltBaslik());
                    dto.setIcerik(onemliBilgiler.getIcerik());
                    dto.setSayfaUrl(onemliBilgiler.getSayfaUrl());
                    dto.setCreatedAt(onemliBilgiler.getCreatedAt());
                    dto.setUpdateAt(onemliBilgiler.getUpdateAt());

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
                    dto.setAktifMi(onemliBilgiler.getAktifMi());
                    dto.setSiraNo(onemliBilgiler.getSiraNo());
                    dto.setGenelDilCategoryId(onemliBilgiler.getGenelDilCategory().getId());
                    dto.setBaslik(onemliBilgiler.getBaslik());
                    dto.setAltBaslik(onemliBilgiler.getAltBaslik());
                    dto.setIcerik(onemliBilgiler.getIcerik());
                    dto.setSayfaUrl(onemliBilgiler.getSayfaUrl());
                    dto.setCreatedAt(onemliBilgiler.getCreatedAt());
                    dto.setUpdateAt(onemliBilgiler.getUpdateAt());
                    return dto;
                })
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "İşlem başarılı.", onemliBilgilerDTOS);
    }

}
