package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.GorevDTO;
import com.sesasis.donusum.yok.dto.GorevDonemiDTO;
import com.sesasis.donusum.yok.dto.IdariBirimDTO;
import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.Gorev;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.entity.IdariBirim;
import com.sesasis.donusum.yok.entity.Personel;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.GorevDonemiRepository;
import com.sesasis.donusum.yok.repository.GorevRepository;
import com.sesasis.donusum.yok.repository.IdariBirimRepository;
import com.sesasis.donusum.yok.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonelService implements IService<PersonalDTO> {

    private final PersonalRepository personalRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final GorevDonemiRepository gorevDonemiRepository;
    private final IdariBirimRepository idariBirimRepository;
    private final GorevRepository gorevRepository;


    @Override
    public ApiResponse save(PersonalDTO personalDTO) {
        boolean personelVarMi = personalRepository.existsByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        return personelVarMi ? handleExistingPersonel(personalDTO) : createNewPersonel(personalDTO);
    }


    private ApiResponse handleExistingPersonel(PersonalDTO personalDTO) {
        Personel mevcutPersonel = personalRepository.findByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        Optional<GorevDonemi> aktifGorevDonemi = gorevDonemiRepository.findByPersonelAndCikisTarihiIsNull(mevcutPersonel);

        IdariBirim idariBirim = idariBirimRepository.findById(personalDTO.getIdariBirimId())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz İdari Birim ID"));
        Gorev gorev = gorevRepository.findById(personalDTO.getGorevId())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz Görev ID"));

        if (aktifGorevDonemi.isPresent()) {
            return new ApiResponse<>(false, "Hata: Personelin çıkış tarihi girilmemiş, yeni kayıt açılamaz.", null);
        } else {
            mevcutPersonel.setAktif(true);
            mevcutPersonel.setGorev(gorev);
            mevcutPersonel.setIdariBirim(idariBirim);

            GorevDonemi yeniGorevDonemi = new GorevDonemi();
            yeniGorevDonemi.setPersonel(mevcutPersonel);
            yeniGorevDonemi.setGirisTarihi(personalDTO.getGirisTarihi());
            yeniGorevDonemi.setCikisTarihi(personalDTO.getCikisTarihi());

            gorevDonemiRepository.save(yeniGorevDonemi);
            return new ApiResponse<>(true, "Personelin yeni dönemi  eklendi.", null);
        }
    }

    private ApiResponse createNewPersonel(PersonalDTO personalDTO) {
        Personel yeniPersonel = this.modelMapperService.request().map(personalDTO, Personel.class);

        Gorev gorev = gorevRepository.findById(personalDTO.getGorevId())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz Görev ID"));

        IdariBirim idariBirim = idariBirimRepository.findById(personalDTO.getIdariBirimId())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz İdari Birim ID"));

        yeniPersonel.setKimlikNumarasi(encodeKimlikNumarasi(personalDTO.getKimlikNumarasi()));
        yeniPersonel.setAktif(true);
        yeniPersonel.setGorev(gorev);
        yeniPersonel.setIdariBirim(idariBirim);
        personalRepository.save(yeniPersonel);

        GorevDonemi yeniGorevDonemi = new GorevDonemi();
        yeniGorevDonemi.setPersonel(yeniPersonel);
        yeniGorevDonemi.setGirisTarihi(personalDTO.getGirisTarihi());
        yeniGorevDonemi.setCikisTarihi(personalDTO.getCikisTarihi());

        gorevDonemiRepository.save(yeniGorevDonemi);
        return new ApiResponse<>(true, "Yeni personel ve giriş tarihi başarıyla kaydedildi.", null);
    }


    public ApiResponse personelCikis(PersonalDTO personalDTO) {
        Personel personel = personalRepository.findByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        if (personel == null) {
            return new ApiResponse<>(false, "Personel bulunamadı.", null);
        }

        GorevDonemi gorevDonemi = gorevDonemiRepository.findByPersonelAndCikisTarihiIsNull(personel)
                .orElseThrow(() -> new IllegalStateException("Personelin çıkış tarihi daha önce girilmiş."));
        gorevDonemi.setCikisTarihi(personalDTO.getCikisTarihi());
        gorevDonemi.setPersonel(personel);
        personel.setAktif(false);
        gorevDonemiRepository.save(gorevDonemi);

        return new ApiResponse<>(true, "Personelin çıkış tarihi başarıyla güncellendi", null);
    }


    @Override
    public ApiResponse findAll() {
        List<Personel> personels = personalRepository.findAll();
        if (personels.isEmpty()) {
            return new ApiResponse<>(false, "Personel listesi bulunamadı.", null);
        }

        List<PersonalDTO> personalDTOS = personels.stream().map(personel -> {
            PersonalDTO personalDTO = this.modelMapperService.response().map(personel, PersonalDTO.class);

            if (personel.getGorev() != null) {
                GorevDTO gorevDTO = this.modelMapperService.response().map(personel.getGorev(), GorevDTO.class);
                personalDTO.setGorevId(gorevDTO.getId());
            }

            if (personel.getIdariBirim() != null) {
                IdariBirimDTO idariBirimDTO = this.modelMapperService.response().map(personel.getIdariBirim(), IdariBirimDTO.class);
                personalDTO.setIdariBirimId(idariBirimDTO.getId());
            }

            List<GorevDonemiDTO> gorevDonemiDTOS = personel.getGorevDonemleri().stream().map(gorevDonemi -> {
                GorevDonemiDTO gorevDonemiDTO = this.modelMapperService.response().map(gorevDonemi, GorevDonemiDTO.class);
                gorevDonemiDTO.setGorevDonemId(gorevDonemi.getId());
                return gorevDonemiDTO;
            }).collect(Collectors.toList());
            personalDTO.setGorevDonemleri(gorevDonemiDTOS);

            return personalDTO;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true, "Personel listesi başarıyla getirildi.", personalDTOS);
    }


    @Override
    public ApiResponse findById(Long id) {
        Personel personel = personalRepository.findById(id).orElse(null);
        if (personel == null) {
            return new ApiResponse<>(false, "Personel bulunamadı.", null);
        }
        PersonalDTO dto = this.modelMapperService.response().map(personel, PersonalDTO.class);
        if (personel.getGorev() != null) {
            Gorev gorev = this.modelMapperService.response().map(personel.getGorev(), Gorev.class);
            dto.setGorevId(gorev.getId());
        }
        if (personel.getIdariBirim() != null) {
            IdariBirim idariBirim = this.modelMapperService.response().map(personel.getIdariBirim(), IdariBirim.class);
            dto.setIdariBirimId(idariBirim.getId());
        }

        List<GorevDonemiDTO> gorevDonemiDTOS = personel.getGorevDonemleri().stream().map(gorevDonemi -> {
            GorevDonemiDTO gorevDonemiDTO = this.modelMapperService.response().map(gorevDonemi, GorevDonemiDTO.class);
            gorevDonemiDTO.setGorevDonemId(gorevDonemi.getId());
            return gorevDonemiDTO;
        }).collect(Collectors.toList());
        dto.setGorevDonemleri(gorevDonemiDTOS);
        return new ApiResponse<>(true, "Personel bulundu.", dto);
    }

    @Override
    public void deleteById(Long id) {
        if (personalRepository.existsById(id)) {
            personalRepository.deleteById(id);
        }
    }

    public ApiResponse findByKimlikNumarasi(String kimlikNumarasi) {
        Personel personel = personalRepository.findByKimlikNumarasi(kimlikNumarasi);
        if (personel == null) {
            return new ApiResponse<>(false, "Personel bulunamadı.", null);
        }
        PersonalDTO dto = this.modelMapperService.response().map(personel, PersonalDTO.class);
        return new ApiResponse<>(true, "Personel bulundu.", dto);
    }
    private String encodeKimlikNumarasi(String kimlikNumarasi) {
        return Base64.getEncoder().encodeToString(kimlikNumarasi.getBytes());
    }

}
