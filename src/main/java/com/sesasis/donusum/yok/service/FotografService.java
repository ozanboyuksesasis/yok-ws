package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.FotografDTO;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.entity.Slider;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.FotografRepository;
import com.sesasis.donusum.yok.repository.SliderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class FotografService implements IService<FotografDTO> {
    private final FotografRepository fotografRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final SliderRepository sliderRepository;


    @Override
    public ApiResponse save(FotografDTO fotografDTO) {

        Slider slider = null;
        if (fotografDTO.getSliderId() != null) {
            slider = sliderRepository.findById(fotografDTO.getSliderId()).orElse(null);
        }
        Fotograf fotograf = this.modelMapperService.request().map(fotografDTO, Fotograf.class);
        Long maxSira = this.fotografRepository.findMaxSiraNo().orElse(0L);

        fotograf.setSlider(slider);
        fotograf.setSiraNo(maxSira + 1);
        fotograf.setCreatedDate(LocalDate.now());

        fotografRepository.save(fotograf);
        FotografDTO dto = this.modelMapperService.response().map(fotograf, FotografDTO.class);

        return new ApiResponse<>(true, "İşlem başarılı.", dto);
    }

    @Override
    public ApiResponse findAll() {

            List<Fotograf> fotografs = fotografRepository.findAll();
            if (fotografs.isEmpty()) {
                return  new ApiResponse<>(false,"Liste boş.",null);
            }
            List<FotografDTO> dtos = fotografs.stream().map(fotograf ->
                    this.modelMapperService.response().map(fotograf, FotografDTO.class)).collect(Collectors.toList());
            return new ApiResponse<>(true, "İşlem başarılı.", dtos);
        }


    @Override
    public ApiResponse findById(Long id) {
        try {
            Fotograf fotograf = this.fotografRepository.findById(id).orElse(null);
            if (fotograf==null){
                return new ApiResponse<>(false,"Fotoğraf bulunamadı.",null);
            }
            FotografDTO fotografDTO = this.modelMapperService.response().map(fotograf, FotografDTO.class);
            return new ApiResponse<>(true,"İşlem başarılı.", fotografDTO);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (fotografRepository.existsById(id)) {
            fotografRepository.deleteById(id);
            siraNoGuncelle();
        }
    }

    @Transactional
    public ApiResponse siraNoGuncelle() {
        List<Fotograf> fotografs = this.fotografRepository.findAll();

        if (fotografs.isEmpty()) {
            return new ApiResponse(false, "Sıra güncellemesi yapılacak duyuru bulunamadı.", null);
        }

        long index = 1;
        for (Fotograf fotograf : fotografs) {
            fotograf.setSiraNo(index++);
        }
        fotografRepository.saveAll(fotografs);
        return new ApiResponse<>(true, "Sıra güncellendi.", null);
    }
    public ApiResponse sliderNoEkle(Long sliderId,Long fotografId) {
        try {
            Slider slider = sliderRepository.findById(sliderId).orElse(null);
            if (slider==null){
                return new ApiResponse<>(false,"Slider bulunamadı.",null);
            }
            Fotograf fotograf =this.fotografRepository.findById(fotografId).orElse(null);
            if (fotograf==null){
                return new ApiResponse<>(false,"Fotoğraf bulunamadı.",null);
            }

            fotograf.setSlider(slider);
            fotografRepository.save(fotograf);
            FotografDTO fotografDTO = this.modelMapperService.response().map(fotograf, FotografDTO.class);
            return new ApiResponse<>(true,"İşlem başarılı.",fotografDTO);
        }catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }

    }

}
