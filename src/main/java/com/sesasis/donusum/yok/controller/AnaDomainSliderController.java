package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.dto.AnaDomainSliderDTO;
import com.sesasis.donusum.yok.service.AnaDomainSliderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/ana-domain-slider")
@RequiredArgsConstructor
public class AnaDomainSliderController {

    private final AnaDomainSliderService anaDomainSliderService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnaDomainSliderDTO> createSlider(@RequestBody AnaDomainSliderDTO anaDomainSliderDTO) {
        return ResponseEntity.ok(anaDomainSliderService.save(anaDomainSliderDTO));
    }

    @PostMapping(value = "/save-with-file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> saveWithFile(
            @RequestPart("model") AnaDomainSliderDTO model,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        return ResponseEntity.ok(anaDomainSliderService.saveWithFile(model, files));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnaDomainSliderDTO>> getAllSliders() {
        return ResponseEntity.ok(anaDomainSliderService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnaDomainSliderDTO> getSliderById(@PathVariable Long id) {
        return ResponseEntity.ok(anaDomainSliderService.findById(id));
    }

    @DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
    public ResponseEntity<Void> deleteSlider(@PathVariable Long id) {
        anaDomainSliderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}