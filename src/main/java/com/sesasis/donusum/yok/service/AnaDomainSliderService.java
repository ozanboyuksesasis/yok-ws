package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.dto.AnaDomainSliderDTO;
import com.sesasis.donusum.yok.entity.AnaDomainSlider;
import com.sesasis.donusum.yok.repository.AnaDomainSliderRepository;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnaDomainSliderService {

    private final AnaDomainSliderRepository anaDomainSliderRepository;
    private final ModelMapperServiceImpl modelMapperService;

    public AnaDomainSliderService(AnaDomainSliderRepository anaDomainSliderRepository, ModelMapperServiceImpl modelMapperService) {
        this.anaDomainSliderRepository = anaDomainSliderRepository;
        this.modelMapperService = modelMapperService;
    }

    @Transactional
    public AnaDomainSliderDTO save(AnaDomainSliderDTO anaDomainSliderDTO) {
        AnaDomainSlider anaDomainSlider = modelMapperService.request().map(anaDomainSliderDTO, AnaDomainSlider.class);
        anaDomainSlider = anaDomainSliderRepository.save(anaDomainSlider);
        return modelMapperService.response().map(anaDomainSlider, AnaDomainSliderDTO.class);
    }

    @Transactional
    public AnaDomainSliderDTO saveWithFile(AnaDomainSliderDTO anaDomainSliderDTO, MultipartFile[] files) {
        AnaDomainSlider anaDomainSlider = modelMapperService.request().map(anaDomainSliderDTO, AnaDomainSlider.class);
        if (files != null && files.length > 0) {
            try {
                anaDomainSlider.setFoto(files[0].getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file", e);
            }
        }
        anaDomainSlider = anaDomainSliderRepository.save(anaDomainSlider);
        return modelMapperService.response().map(anaDomainSlider, AnaDomainSliderDTO.class);
    }

    public List<AnaDomainSliderDTO> findAll() {
        return anaDomainSliderRepository.findAll().stream()
                .map(slider -> modelMapperService.response().map(slider, AnaDomainSliderDTO.class))
                .collect(Collectors.toList());
    }

    public AnaDomainSliderDTO findById(Long id) {
        return anaDomainSliderRepository.findById(id)
                .map(slider -> modelMapperService.response().map(slider, AnaDomainSliderDTO.class))
                .orElse(null);
    }

    public void deleteById(Long id) {
        anaDomainSliderRepository.deleteById(id);
    }
}