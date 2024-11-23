package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.OnemliBilgilerDTO;
import com.sesasis.donusum.yok.service.OnemliBilgilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;

@RestController
@RequestMapping(value = "/api/onemli-bilgi")
@RequiredArgsConstructor
public class OnemliBilgilerController {

    private final OnemliBilgilerService onemliBilgilerService;


    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody OnemliBilgilerDTO onemliBilgilerDTO) {
        ApiResponse save = onemliBilgilerService.save(onemliBilgilerDTO);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping(value = "/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        onemliBilgilerService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse<>(true,"Silme işlemi başarılıdır.",null), HttpStatus.OK);
    }

}
