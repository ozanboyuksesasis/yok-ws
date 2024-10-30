package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.service.DuyuruService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/duyurular")
@RequiredArgsConstructor
public class DuyuruController {

    private final DuyuruService duyuruService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody DuyuruDTO duyuruDTO){
        ApiResponse save = duyuruService.save(duyuruDTO);
    return ResponseEntity.ok(save);
    }
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        ApiResponse response = duyuruService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApiResponse response = duyuruService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        duyuruService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Duyuru başarıyla silindi.", null));
    }

    @PostMapping(value = "/{newDomainId}/{duyuruId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addDomain( @PathVariable Long newDomainId, @PathVariable  Long duyuruId) {
        ApiResponse response = duyuruService.DomainEkle(newDomainId, duyuruId);
        return ResponseEntity.ok(response);
    }

}
