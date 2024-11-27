package com.sesasis.donusum.yok.controller;


import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.GenelDilCategoryDTO;
import com.sesasis.donusum.yok.service.GenelDilCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dil-category")
public class GenelDilCategoryController {

    private final GenelDilCategoryService genelDilCategoryService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save (@Valid @RequestBody GenelDilCategoryDTO dilCategoryDTO){
        ApiResponse save = genelDilCategoryService.save(dilCategoryDTO);
        return ResponseEntity.ok(save);
    }

    @GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        ApiResponse all = genelDilCategoryService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id){
        ApiResponse haberDilCategory = genelDilCategoryService.findById(id);
        return ResponseEntity.ok(haberDilCategory);
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete (@PathVariable Long id){
        genelDilCategoryService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Dil kategori başarı ile silindi.",null));
    }



}
