package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.SliderDTO;
import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.entity.Slider;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import com.sesasis.donusum.yok.repository.SliderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class SliderService implements IService<SliderDTO> {

    private final SliderRepository sliderRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final NewDomainsRepository newDomainsRepository;

    @Override
    public ApiResponse save(SliderDTO sliderDTO) {
        try {
            NewDomain newDomain = null;
            if (sliderDTO.getNewDomainId() != null) {
                newDomain = newDomainsRepository.findById(sliderDTO.getNewDomainId()).orElse(null);
            }
            Slider slider = this.modelMapperServiceImpl.request().map(sliderDTO, Slider.class);
            slider.setNewDomain(newDomain);

            long number = sliderRepository.findMaxSiraNo().orElse(0L);
            slider.setSiraNo(number + 1);
            sliderRepository.save(slider);

            SliderDTO DTO = this.modelMapperServiceImpl.response().map(slider, SliderDTO.class);
            return new ApiResponse<>(true, "Kayıt başarılı.", DTO);

        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, "Kayıt sırasında bir hata oluştu. Lütfen tekrar deneyin.", null);
        }
    }


    @Override
    public ApiResponse findAll() {
        List<Slider> sliders = sliderRepository.findAllByOrderBySiraNoDesc();
        if (sliders.isEmpty()) {
            return new ApiResponse(false,"Liste boş.",null);
        }

        List<SliderDTO> dtos = sliders.stream().map(slider ->
                this.modelMapperServiceImpl.response().map(slider,SliderDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
       Slider slider = sliderRepository.findById(id).
               orElseThrow(()-> new RuntimeException("Slider bulunamadı."));

       SliderDTO sliderDTO = this.modelMapperServiceImpl.response().map(slider, SliderDTO.class);

        return new ApiResponse<>(true,"İşlem başarılı.",sliderDTO);
    }

    @Override
    public void deleteById(Long id) {
    if (sliderRepository.existsById(id)) {
        sliderRepository.deleteById(id);
      siraNoGuncelle();
    }
    }

    public ApiResponse domainEkle(Long newDomainId, Long sliderId) {
        try {
            NewDomain newDomain = newDomainsRepository.findById(newDomainId)
                    .orElseThrow(() -> new RuntimeException("Domain bulunamadı: " + newDomainId));

            Slider slider = sliderRepository.findById(sliderId)
                    .orElseThrow(() -> new RuntimeException("Duyuru bulunamadı: " + sliderId));

            slider.setNewDomain(newDomain);
            sliderRepository.save(slider);

            return new ApiResponse<>(true, "Domain ekleme işlemi başarılı.", null);

        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        } catch (Exception ex) {
            return new ApiResponse<>(false, "Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin.", null);
        }
    }

    @Transactional
    public ApiResponse siraNoGuncelle() {
        List<Slider> sliders = this.sliderRepository.findAllByOrderBySiraNoDesc();
        if (sliders.isEmpty()) {
            return new ApiResponse(false, "Sıra güncellemesi yapılacak duyuru bulunamadı.", null);
        }
        long index = 1;
        for (Slider haber : sliders) {
            haber.setSiraNo(index++);
        }
        sliderRepository.saveAll(sliders);
        return new ApiResponse<>(true, "Sıra güncellendi.", null);
    }


}
