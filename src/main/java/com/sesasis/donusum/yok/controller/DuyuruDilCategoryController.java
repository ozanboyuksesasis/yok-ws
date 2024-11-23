package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DuyuruDilCategoryDTO;
import com.sesasis.donusum.yok.service.DuyuruDilCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/duyuru-dil")
@RequiredArgsConstructor
public class DuyuruDilCategoryController {

    private final DuyuruDilCategoryService duyuruDilCategoryService;



    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody DuyuruDilCategoryDTO dto) {
        ApiResponse save = duyuruDilCategoryService.save(dto);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        ApiResponse all = duyuruDilCategoryService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        duyuruDilCategoryService.deleteById(id);
    return new ResponseEntity<>(new ApiResponse<>(true,"Silme işlemi başarılıdır.",null),HttpStatus.OK);
    }
}
