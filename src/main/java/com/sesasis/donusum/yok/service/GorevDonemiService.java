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
