package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.service.GorevDonemiService;
import com.sesasis.donusum.yok.service.PersonelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/personel")
@RequiredArgsConstructor
public class PersonelController {

    private final PersonelService personelService;
    private final GorevDonemiService gorevDonemiService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody PersonalDTO personalDTO) {
        return ResponseEntity.ok(personelService.save(personalDTO));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(personelService.findAll());
    }

    @GetMapping(value = MappingConstants.PATH_VARIABLE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(personelService.findById(id));
    }

    @DeleteMapping(value = MappingConstants.PATH_VARIABLE_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        personelService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/cikis")
    public ResponseEntity<?> personelCikis(@RequestBody PersonalDTO personelDTO) {
        ApiResponse response = personelService.personelCikis(personelDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/kimlik/{kimlikNumarasi}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByKimlikNumarasi(@PathVariable String kimlikNumarasi) {
        ApiResponse response = personelService.findByKimlikNumarasi(kimlikNumarasi);
        return ResponseEntity.ok(response);
    }




}


