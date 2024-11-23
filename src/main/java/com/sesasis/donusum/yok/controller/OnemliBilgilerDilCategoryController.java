package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.OnemliBilgilerDilCategoryDTO;
import com.sesasis.donusum.yok.service.OnemliBilgilerDilCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/önemli-bilgi-dil-category")
@RequiredArgsConstructor
public class OnemliBilgilerDilCategoryController {

    private final OnemliBilgilerDilCategoryService dilCategoryService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid  @RequestBody OnemliBilgilerDilCategoryDTO dto){
        ApiResponse save = dilCategoryService.save(dto);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        ApiResponse all = dilCategoryService.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        dilCategoryService.deleteById(id);

        return new ResponseEntity<>(new ApiResponse<>(true,"Silme işlemi başarılı.",null),HttpStatus.OK);
    }

}
