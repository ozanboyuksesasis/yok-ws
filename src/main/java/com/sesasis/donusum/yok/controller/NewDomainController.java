package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.NewDomainDTO;
import com.sesasis.donusum.yok.service.NewDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

@RestController
@RequestMapping("/api/new-domains")
@RequiredArgsConstructor
public class NewDomainController {

    private final NewDomainService newDomainService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody NewDomainDTO newDomainDTO) {
        ApiResponse save = newDomainService.save(newDomainDTO);
        return ResponseEntity.ok(save);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        ApiResponse all = newDomainService.findAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApiResponse response = newDomainService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        newDomainService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
