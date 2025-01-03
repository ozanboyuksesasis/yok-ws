package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.GorevDonemiDTO;
import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.entity.Personel;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GorevDonemiRepository;
import com.sesasis.donusum.yok.repository.PersonalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GorevDonemiService implements IService<GorevDonemiDTO> {

   private final GorevDonemiRepository gorevDonemiRepository;
   private final PersonalRepository personalRepository;
   private final ModelMapperServiceImpl modelMapperServiceImpl;

    public GorevDonemiService(GorevDonemiRepository gorevDonemiRepository, PersonalRepository personalRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
        this.gorevDonemiRepository = gorevDonemiRepository;
        this.personalRepository = personalRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }

    public ApiResponse updateTarih(Long gorevDonemiID, GorevDonemiDTO gorevDonemiDTO) {
        LocalDate girisDate = gorevDonemiDTO.getGirisTarihi();
        LocalDate cikisDate = gorevDonemiDTO.getCikisTarihi();

        Optional<GorevDonemi> gorevDonemiOptional = gorevDonemiRepository.findById(gorevDonemiID);

        if (gorevDonemiOptional.isEmpty()) {
            return new ApiResponse<>(false, "Görev dönemi bulunamadı.", null);
        }

        GorevDonemi mevcutGorevDonemi = gorevDonemiOptional.get();
        mevcutGorevDonemi.setGirisTarihi(girisDate);
        mevcutGorevDonemi.setCikisTarihi(cikisDate);

        gorevDonemiRepository.save(mevcutGorevDonemi);


        return new ApiResponse<>(true, "Giriş ve çıkış tarihleri başarıyla güncellendi.", null);
    }



    @Override
    public ApiResponse save(GorevDonemiDTO gorevDonemiDTO) {

        return null;
    }

    @Override
    public ApiResponse findAll() {
        List<Personel> personels = personalRepository.findAll();
        if (personels.isEmpty()){
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<PersonalDTO> personalDTOS = personels.stream().map(personel ->
            this.modelMapperServiceImpl.response().
                    map(personel, PersonalDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true,"Personel listesi ve görev dönemleri.",personalDTOS);
    }

    @Override
    public ApiResponse findById(Long id) {
        Personel personel = personalRepository.findById(id).orElse(null);
        if (personel == null) {
            return new ApiResponse<>(false,"Personel bulunamadı.",null);
        }
        PersonalDTO personalDTO = this.modelMapperServiceImpl.response().
                map(personel, PersonalDTO.class);
        return new ApiResponse<>(true,"Personel ve görev listesi bulundu.",personalDTO);
    }

    @Override
    public void deleteById(Long id) {

    }
}
