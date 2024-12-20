package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DuyuruDTO;
import com.sesasis.donusum.yok.service.DuyuruService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/duyuru")
@RequiredArgsConstructor
public class DuyuruController {

    private final DuyuruService duyuruService;


    @PostMapping(value = "/list-save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody List<DuyuruDTO> duyuruDTO){
        ApiResponse save = duyuruService.listSave(duyuruDTO);
        return ResponseEntity.ok(save);
    }

    @GetMapping(value = "/get-haber-by-domainId/{domainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDuyurusByDomainId(@PathVariable Long domainId) {
        if (domainId == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Domain ID is required.", null));
        }
        ApiResponse response = duyuruService.getDuyurusDomainId(domainId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/update-save",produces = MediaType.APPLICATION_JSON_VALUE)
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
