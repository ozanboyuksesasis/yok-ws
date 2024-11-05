package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.GorevDTO;
import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.Gorev;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GorevRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GorevService implements IService<GorevDTO> {

    private final ModelMapperServiceImpl modelMapperService;
    private final GorevRepository gorevRepository;

    public GorevService(ModelMapperServiceImpl modelMapperService, GorevRepository gorevRepository) {
        this.modelMapperService = modelMapperService;
        this.gorevRepository = gorevRepository;
    }

    @Override
    public ApiResponse save(GorevDTO gorevDTO) {
        Gorev gorev = this.modelMapperService.request().map(gorevDTO, Gorev.class);
        Gorev save = gorevRepository.save(gorev);
        GorevDTO dto = this.modelMapperService.response().map(save, GorevDTO.class);

        return new ApiResponse<>(true, "İşlem başarılı.", dto);
    }


    @Override
    public ApiResponse findAll() {
        List<Gorev> gorevList = gorevRepository.findAll();

        if (gorevList.isEmpty()) {
            return new ApiResponse<>(true, "Liste boş.", Collections.emptyList());
        }

        List<GorevDTO> gorevDTOS = gorevList.stream()
                .map(gorev -> this.modelMapperService.response().map(gorev, GorevDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true, "İşlem başarılı.", gorevDTOS);
    }

    @Override
    public ApiResponse findById(Long id) {
        Gorev gorev = gorevRepository.findById(id).orElse(null);
        if (gorev == null) {
            return new ApiResponse<>(false, "Görev bulunamadı.", null);
        }
        GorevDTO gorevDTO = modelMapperService.response().map(gorev, GorevDTO.class);

        return new ApiResponse<>(true, "İşlem başarılı.", gorevDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (gorevRepository.existsById(id)) {
            gorevRepository.deleteById(id);
        }
    }


    public ApiResponse gorevVePersoneller(Long id) {
        Gorev gorev = gorevRepository.findById(id).orElse(null);
        if (gorev == null) {
            return new ApiResponse<>(false, "Görev bulunamadı.", null);
        }
        GorevDTO gDto = this.modelMapperService.response().map(gorev, GorevDTO.class);

        if (gorev.getPersonels() != null && !gorev.getPersonels().isEmpty()) {
            List<PersonalDTO> personalDTOS = gorev.getPersonels().stream()
                    .map(personel -> this.modelMapperService.response().map(personel, PersonalDTO.class))
                    .collect(Collectors.toList());
            gDto.setPersonals(personalDTOS);
        }

        return new ApiResponse<>(true, "Görev ve personeller başarıyla getirildi.", gDto);
    }

}
