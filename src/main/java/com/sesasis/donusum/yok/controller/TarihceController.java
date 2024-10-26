package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.dto.TarihceDTO;
import com.sesasis.donusum.yok.service.TarihceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarihce")
@RequiredArgsConstructor
public class TarihceController {

    private final TarihceService tarihceService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody TarihceDTO tarihceDTO) {
        return ResponseEntity.ok(tarihceService.save(tarihceDTO));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(tarihceService.findAll());
    }

    @GetMapping(value = MappingConstants.PATH_VARIABLE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tarihceService.findById(id));
    }

    @DeleteMapping(value = MappingConstants.PATH_VARIABLE_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tarihceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
