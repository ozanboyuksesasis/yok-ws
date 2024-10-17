package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.GorevDonemiDTO;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.entity.Personel;
import com.sesasis.donusum.yok.repository.GorevDonemiRepository;
import com.sesasis.donusum.yok.repository.PersonalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GorevDonemiService implements IService<GorevDonemiDTO> {

   private final GorevDonemiRepository gorevDonemiRepository;
   private final PersonalRepository personalRepository;

    public GorevDonemiService(GorevDonemiRepository gorevDonemiRepository, PersonalRepository personalRepository) {
        this.gorevDonemiRepository = gorevDonemiRepository;
        this.personalRepository = personalRepository;
    }
    public ApiResponse personelCikis(String kimlikNumarasi, LocalDate cikisTarihi) {
        Personel personel = personalRepository.findByKimlikNumarasi(kimlikNumarasi);
        if (personel == null) {
            return new ApiResponse<>(false, "Personel bulunamadı.", null);
        }

        GorevDonemi gorevDonemi = gorevDonemiRepository.findByPersonelAndCikisTarihiIsNull(personel)
                .orElseThrow(() -> new IllegalStateException("Personelin çıkış tarihi daha önce girilmiş."));
        gorevDonemi.setCikisTarihi(cikisTarihi);
        gorevDonemi.setPersonel(personel);
        personel.setAktif(false);
        gorevDonemiRepository.save(gorevDonemi);

        return new ApiResponse<>(true, "Personelin çıkış tarihi başarıyla güncellendi", null);
    }



    @Override
    public ApiResponse save(GorevDonemiDTO gorevDonemiDTO) {
        return null;
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

    }
}
