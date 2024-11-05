package com.sesasis.donusum.yok.controller;


import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.dto.HaberDTO;
import com.sesasis.donusum.yok.service.HaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/habers")
@RequiredArgsConstructor
public class HaberController {

    private final HaberService haberService;


    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody HaberDTO haberDTO){
        ApiResponse save = haberService.save(haberDTO);
        return ResponseEntity.ok(save);
    }
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        ApiResponse response = haberService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApiResponse response = haberService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        haberService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Haber başarıyla silindi.", null));
    }

    @PostMapping(value = "/{newDomainId}/{duyuruId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addDomain( @PathVariable Long newDomainId, @PathVariable  Long duyuruId) {
        ApiResponse response = haberService.DomainEkle(newDomainId, duyuruId);
        return ResponseEntity.ok(response);
    }
}
