package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.entity.Personel;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GorevDonemiRepository;
import com.sesasis.donusum.yok.repository.PersonalRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonelService implements IService<PersonalDTO> {

    private final PersonalRepository personalRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final GorevDonemiRepository gorevDonemiRepository;

    public PersonelService(PersonalRepository personalRepository, ModelMapperServiceImpl modelMapperService, GorevDonemiRepository gorevDonemiRepository) {
        this.personalRepository = personalRepository;
        this.modelMapperService = modelMapperService;
        this.gorevDonemiRepository = gorevDonemiRepository;
    }

    @Override
    public ApiResponse save(PersonalDTO personalDTO) {
        if (personalDTO.getIsim() == null || personalDTO.getIsim().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: İsim boş olamaz.", null);
        }
        if (personalDTO.getSoyisim() == null || personalDTO.getSoyisim().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: Soyisim boş olamaz.", null);
        }
        if (personalDTO.getUnvan() == null || personalDTO.getUnvan().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: Unvan boş olamaz.", null);
        }

        boolean personelVarMi = personalRepository.existsByKimlikNumarasi(personalDTO.getKimlikNumarasi());

        if (personelVarMi) {
            Personel mevcutPersonel = personalRepository.findByKimlikNumarasi(personalDTO.getKimlikNumarasi());
            Optional<GorevDonemi> aktifGorevDonemi = gorevDonemiRepository.findByPersonelAndCikisTarihiIsNull(mevcutPersonel);

            if (aktifGorevDonemi.isPresent()) {
                return new ApiResponse<>(false, "Hata: Personelin çıkış tarihi girilmemiş, yeni kayıt açılamaz.", null);
            } else {
                GorevDonemi yeniGorevDonemi = new GorevDonemi();
                yeniGorevDonemi.setPersonel(mevcutPersonel);
                yeniGorevDonemi.setGirisTarihi(personalDTO.getGirisTarihi()); // Giriş tarihi manuel girilecek
                yeniGorevDonemi.setCikisTarihi(null); // Çıkış tarihi boş kalacak
                gorevDonemiRepository.save(yeniGorevDonemi);

                return new ApiResponse<>(true, "Eski personelin yeni giriş tarihi kaydedildi", null);
            }
        }

        Personel personel = this.modelMapperService.request().map(personalDTO, Personel.class);
        GorevDonemi gorevDonemi = new GorevDonemi();
        gorevDonemi.setPersonel(personel);
        gorevDonemi.setGirisTarihi(personalDTO.getGirisTarihi());
        gorevDonemi.setCikisTarihi(null);
        personalRepository.save(personel);
        gorevDonemiRepository.save(gorevDonemi);

        return new ApiResponse<>(true, "Personel başarı ile kayıt edildi", personel);
    }



    @Override
    public ApiResponse findAll() {
        List<Personel> personels = personalRepository.findAll();
        if (personels.isEmpty()) {
            return new ApiResponse<>(false,"Personel listesi bulunamadı.",null);
        }
        List<PersonalDTO> personalDTOS=personels.stream().map(personel ->
                this.modelMapperService.response().map(personel,PersonalDTO.class)).collect(Collectors.toList());
        return new ApiResponse<>(true,"Personel listesi başarı ile bulundu.",personalDTOS);
    }

    @Override
    public ApiResponse findById(Long id) {
        Personel personel = personalRepository.findById(id).orElse(null);
        if (personel == null) {
            return new ApiResponse<>(false,"Personel bulunamadı.",null);
        }
        PersonalDTO personalDTO = this.modelMapperService.response().map(personel,PersonalDTO.class);
        return new ApiResponse<>(true,"Personel bulundu.",personel);
    }

    @Override
    public void deleteById(Long id) {
    if (personalRepository.existsById(id)){
        personalRepository.deleteById(id);
    }
    }

}
