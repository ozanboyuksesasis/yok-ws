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

@Service
public class GorevDonemiService implements IService<GorevDonemiDTO> {

   private final GorevDonemiRepository gorevDonemiRepository;
   private final PersonalRepository personalRepository;

    public GorevDonemiService(GorevDonemiRepository gorevDonemiRepository, PersonalRepository personalRepository) {
        this.gorevDonemiRepository = gorevDonemiRepository;
        this.personalRepository = personalRepository;
    }
    public ApiResponse personelCikis(Long personelId, LocalDate cikisTarihi) {

        GorevDonemi aktifGorevDonemi = gorevDonemiRepository.findByPersonelIdAndCikisTarihiIsNull(personelId);

        if (aktifGorevDonemi == null) {
            return new ApiResponse<>(false, "Personelin aktif bir görev dönemi bulunamadı", null);
        }
        aktifGorevDonemi.setCikisTarihi(cikisTarihi);
        aktifGorevDonemi.getPersonel().setAktif(false);

        // Güncellenen görev dönemini kaydet
        gorevDonemiRepository.save(aktifGorevDonemi);

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
