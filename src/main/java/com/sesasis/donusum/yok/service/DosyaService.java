package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.GeneralUtils;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.DosyaDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Dosya;
import com.sesasis.donusum.yok.enums.DosyaType;
import com.sesasis.donusum.yok.entity.Galeri;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DosyaRepository;
import com.sesasis.donusum.yok.repository.GaleriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DosyaService implements IService<DosyaDTO> {
    private final SecurityContextUtil securityContextUtil;
    private final GaleriRepository galeriRepository;
    private final DosyaRepository dosyaRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final FileService fileService;

    @Override
    public ApiResponse save(DosyaDTO dosyaDTO) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        Galeri galeri = galeriRepository.findById(dosyaDTO.getGaleriId()).orElse(null);
        if (galeri == null) {
            return new ApiResponse<>(false, "Galeri bulunamadı.", null);
        }
        Dosya dosya = modelMapperService.request().map(dosyaDTO, Dosya.class);
        dosya.setGaleri(galeri);
        dosya.setContentDetay(dosyaDTO.getContentDetay().getBytes());
        return null;
    }

    public ApiResponse saveDosya(List<DosyaDTO> dosyaDTO, MultipartFile file) {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        String path = null;
        boolean kaydedildi = false;
        List<Dosya> dosyaList = new ArrayList<>();
        for (DosyaDTO dto : dosyaDTO) {
            if (!kaydedildi) {
                String dosyaName = GeneralUtils.generateFileName(file);
                try {
                    path = fileService.saveFile(file, dosyaName).toFile().getAbsolutePath();
                    kaydedildi = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Dosya dosya = new Dosya();
            if (dto.getDosyaType().equals(DosyaType.HIZLIBAGLANTI)){
                dosya.setDosyaType(DosyaType.HIZLIBAGLANTI);
            }else {
                dosya.setDosyaType(DosyaType.STORIE);
            }
            dosya.setUrl(path);
            dosya.setId(dto.getId());
            dosya.setContentDetay(dto.getContentDetay().getBytes());
            dosya.setDomain(domain);
            dosya.setSiraNo(dto.getSiraNo());
            Galeri galeri = galeriRepository.findById(dto.getGaleriId()).orElse(null);
            if (galeri == null) {
                return new ApiResponse<>(false, "Galeri bulunamadı.", null);
            }
            dosya.setGaleri(galeri);
            dosyaList.add(dosya);
        }
        if (GeneralUtils.valueNullOrEmpty(path)) {
            dosyaRepository.saveAll(dosyaList);
            return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
        } else {
            return new ApiResponse(false, "Dosya oluşturulamadı.", null);
        }
    }


    @Override
    public ApiResponse findAll() {
        Domain domain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (domain == null) {
            return new ApiResponse<>(false, "Domain bulunamadı.", null);
        }
        List<Dosya> dosyaList = dosyaRepository.findAllByDomain_Id(domain.getId());
        if (dosyaList.isEmpty()) {
            return new ApiResponse<>(false, "Liste boş.", null);
        }
        List<DosyaDTO> dtos = dosyaList.stream().map(dosya -> this.modelMapperService.response().map(dosya, DosyaDTO.class)).collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
