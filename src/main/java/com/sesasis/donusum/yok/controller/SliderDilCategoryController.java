package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.SliderDilCategoryDTO;
import com.sesasis.donusum.yok.service.SliderDilCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.stream.events.EntityReference;

@RestController
@RequestMapping(value = "/api/slider-dil")
@RequiredArgsConstructor
public class SliderDilCategoryController {

    private final SliderDilCategoryService sliderDilCategoryService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SliderDilCategoryDTO dilCategoryDTO) {
        ApiResponse save = sliderDilCategoryService.save(dilCategoryDTO);
        return ResponseEntity.ok(save);
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        ApiResponse all = sliderDilCategoryService.findAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ApiResponse byId = sliderDilCategoryService.findById(id);
        return ResponseEntity.ok(byId);
    }



}
