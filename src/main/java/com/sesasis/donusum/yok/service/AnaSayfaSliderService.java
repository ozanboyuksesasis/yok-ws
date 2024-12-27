package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.GeneralUtils;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import com.sesasis.donusum.yok.entity.GenelDilCategory;
import com.sesasis.donusum.yok.repository.AnaSayfaSliderRepository;
import com.sesasis.donusum.yok.repository.GenelDilCategoryRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnaSayfaSliderService extends AbstractService<AnaSayfaSlider, AnaSayfaSliderRepository> implements IService<AnaSayfaSliderDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final MenuRepository menuRepository;
    private final FileService fileService;
    private final GenelDilCategoryRepository genelDilCategoryRepository;


    public AnaSayfaSliderService(AnaSayfaSliderRepository repository, SecurityContextUtil securityContextUtil, MenuRepository menuRepository, FileService fileService, GenelDilCategoryRepository genelDilCategoryRepository) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.menuRepository = menuRepository;
        this.fileService = fileService;
        this.genelDilCategoryRepository = genelDilCategoryRepository;
    }

    @Override
    @Transactional
    public ApiResponse save(AnaSayfaSliderDTO anaSayfaSliderDTO) {
        if (anaSayfaSliderDTO.getGenelDilCategoryId() == null) {
            throw new IllegalArgumentException("GenelDilCategory ID must not be null");
        }
        GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(anaSayfaSliderDTO.getGenelDilCategoryId())
                .orElseThrow(() -> new RuntimeException("GenelDilCategory not found: " + anaSayfaSliderDTO.getGenelDilCategoryId()));
        AnaSayfaSlider anaSayfaSlider = anaSayfaSliderDTO.toEntity();
        anaSayfaSlider.setGenelDilCategory(genelDilCategory);
        getRepository().save(anaSayfaSlider);
        return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse findAll() {
        List<AnaSayfaSliderDTO> anaSayfaSliderDTOList = getRepository().findAllByOrderBySiraNoAsc().stream().map(e -> {
            AnaSayfaSliderDTO anaSayfaSliderDTO = e.toDTO();
            try {
                anaSayfaSliderDTO.setBase64content(fileService.getFileAsBase64(e.getPath()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return anaSayfaSliderDTO;
        }).collect(Collectors.toList());

        return new ApiResponse(true, MessageConstant.SUCCESS, anaSayfaSliderDTOList);
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        AnaSayfaSlider anaSayfaSlider = getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Slider bulunamadı"));
        getRepository().delete(anaSayfaSlider);
    }

    @Transactional
    public void deleteBySiraNo(Long siraNo) {
        List<AnaSayfaSlider> sliders = getRepository().findAllBySiraNo(siraNo);
        if (sliders.isEmpty()) {
            throw new RuntimeException("No sliders found with siraNo: " + siraNo);
        }
        getRepository().deleteAll(sliders);
    }


    @Transactional
    public ApiResponse updateSiraNo(Long id, Long newSiraNo) {
        List<AnaSayfaSlider> anaSayfaSliders = getRepository().findSlidersById(id);

        if (anaSayfaSliders.isEmpty()) {
            return new ApiResponse(false, "Slider bulunamadı", null);
        }

        // Update the `siraNo` for the sliders in the `anaSayfaSliders` list
        for (AnaSayfaSlider slider : anaSayfaSliders) {
            slider.setSiraNo(newSiraNo);
        }
        getRepository().saveAll(anaSayfaSliders);

        // Retrieve all sliders ordered by `siraNo`
        List<AnaSayfaSlider> sliders = getRepository().findAllByOrderBySiraNoAsc();

        // Adjust `siraNo` for other sliders
        for (AnaSayfaSlider slider : sliders) {
            if (slider.getSiraNo() >= newSiraNo &&
                    anaSayfaSliders.stream().noneMatch(s -> s.getId().equals(slider.getId()))) {
                slider.setSiraNo(slider.getSiraNo() + 1);
            }
        }
        getRepository().saveAll(sliders);

        // Reorganize `siraNo` starting from 1 with two rows having the same value
        sliders.sort(Comparator.comparing(AnaSayfaSlider::getSiraNo)); // Ensure correct order
        long siraNo = 1;

        for (int i = 0; i < sliders.size(); i += 2) {
            if (i < sliders.size()) {
                sliders.get(i).setSiraNo(siraNo);
            }
            if (i + 1 < sliders.size()) {
                sliders.get(i + 1).setSiraNo(siraNo); // Set the same `siraNo` for the next row
            }
            siraNo++;
        }

        // Save the reordered sliders
        getRepository().saveAll(sliders);

        return new ApiResponse(true, "SiraNo updated successfully", sliders);
    }

    @Transactional
    public ApiResponse saveWithFile(AnaSayfaSliderDTO anaSayfaSliderDTO, MultipartFile[] files) {
        if (anaSayfaSliderDTO.getGenelDilCategoryId() == null) {
            throw new IllegalArgumentException("GenelDilCategory ID must not be null");
        }
        GenelDilCategory genelDilCategory = genelDilCategoryRepository.findById(anaSayfaSliderDTO.getGenelDilCategoryId())
                .orElseThrow(() -> new RuntimeException("GenelDilCategory not found: " + anaSayfaSliderDTO.getGenelDilCategoryId()));
        AnaSayfaSlider anaSayfaSlider = anaSayfaSliderDTO.toEntity();
        anaSayfaSlider.setGenelDilCategory(genelDilCategory);
        String path = null;

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String generatedName = GeneralUtils.generateFileName(file);
                try {
                    path = fileService.saveFile(file, generatedName).toFile().getAbsolutePath();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (GeneralUtils.valueNullOrEmpty(anaSayfaSlider.getId())) {
            anaSayfaSlider.setPath(path);
            anaSayfaSlider.setSiraNo(getNextSiraNo(anaSayfaSliderDTO.getGenelDilCategoryId()));
            getRepository().save(anaSayfaSlider);
            return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
        } else {
            if (!GeneralUtils.valueNullOrEmpty(path) && !path.isEmpty()) {
                anaSayfaSlider.setPath(path);
            }
            getRepository().save(anaSayfaSlider);
            return new ApiResponse(true, MessageConstant.UPDATE_MSG, null);
        }
    }
    private Long getNextSiraNo(Long genelDilCategoryId) {
        Long maxSiraNo = getRepository().findAllByGenelDilCategoryIdOrderBySiraNoAsc(genelDilCategoryId).stream()
                .mapToLong(AnaSayfaSlider::getSiraNo)
                .max()
                .orElse(0L);
        return maxSiraNo + 1;
    }
}
