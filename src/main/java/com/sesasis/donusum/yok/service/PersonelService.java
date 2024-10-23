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
    private final IdariBirimRepository idariBirimRepository;
    private final GorevRepository gorevRepository;

    public PersonelService(PersonalRepository personalRepository, ModelMapperServiceImpl modelMapperService, GorevDonemiRepository gorevDonemiRepository, IdariBirimRepository idariBirimRepository, GorevRepository gorevRepository) {
        this.personalRepository = personalRepository;
        this.modelMapperService = modelMapperService;
        this.gorevDonemiRepository = gorevDonemiRepository;
        this.idariBirimRepository = idariBirimRepository;
        this.gorevRepository = gorevRepository;
    }

    @Override
    public ApiResponse save(PersonalDTO personalDTO) {
        ApiResponse validationResponse = saveControl(personalDTO);
        if (!validationResponse.getSuccess()) {
            return validationResponse;
        }

        boolean personelVarMi = personalRepository.existsByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        Personel personel = personalRepository.findByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        IdariBirim idariBirim = idariBirimRepository.findById(personalDTO.getIdariBirimId()).
                orElseThrow(() -> new RuntimeException("Birim bulunamadı."));
        if (personelVarMi) {
            ApiResponse existingPersonelResponse = handleExistingPersonel(personalDTO);
            if (!existingPersonelResponse.getSuccess()) {
                personel.setIdariBirim(idariBirim);
                return existingPersonelResponse;
            }
        } else {
            ApiResponse newPersonelResponse = createNewPersonel(personalDTO);
            return newPersonelResponse;
        }
        return new ApiResponse<>(true, "İşlem başarıyla tamamlandı.", null);
    }

    private ApiResponse handleExistingPersonel(PersonalDTO personalDTO) {
        Personel mevcutPersonel = personalRepository.findByKimlikNumarasi(personalDTO.getKimlikNumarasi());
        Optional<GorevDonemi> aktifGorevDonemi = gorevDonemiRepository.findByPersonelAndCikisTarihiIsNull(mevcutPersonel);

        if (aktifGorevDonemi.isPresent()) {
            return new ApiResponse<>(false, "Hata: Personelin çıkış tarihi girilmemiş, yeni kayıt açılamaz.", null);
        } else {
            mevcutPersonel.setAktif(true);
            GorevDonemi yeniGorevDonemi = new GorevDonemi();
            yeniGorevDonemi.setPersonel(mevcutPersonel);
            yeniGorevDonemi.setGirisTarihi(personalDTO.getGirisTarihi());
            yeniGorevDonemi.setCikisTarihi(personalDTO.getCikisTarihi());
            gorevDonemiRepository.save(yeniGorevDonemi);
            return new ApiResponse<>(true, "İşlem başarıyla tamamlandı.", null);
        }
    }

    private ApiResponse createNewPersonel(PersonalDTO personalDTO) {
        Personel yeniPersonel = this.modelMapperService.response().map(personalDTO, Personel.class);
        yeniPersonel.setAktif(true);

        IdariBirim idariBirim = idariBirimRepository.findById(personalDTO.getIdariBirimId())
                .orElseThrow(() -> new RuntimeException("Idari Birim bulunamadı"));
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

    public ApiResponse saveControl(PersonalDTO personalDTO) {
        if (personalDTO.getIsim() == null || personalDTO.getIsim().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: İsim boş olamaz.", null);
        }
        if (personalDTO.getSoyisim() == null || personalDTO.getSoyisim().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: Soyisim boş olamaz.", null);
        }
        if (personalDTO.getUnvan() == null || personalDTO.getUnvan().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: Unvan boş olamaz.", null);
        }
        if (personalDTO.getKimlikNumarasi() == null || personalDTO.getKimlikNumarasi().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: Kimlik numarası boş olamaz.", null);
        }
        if (personalDTO.getEmail() == null || personalDTO.getEmail().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: E-mail boş olamaz.", null);

        }
        return new ApiResponse<>(true, "Kayıt başarıyla kontrol edildi.", personalDTO);
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
}
