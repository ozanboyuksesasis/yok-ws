package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.IdariBirimDTO;
import com.sesasis.donusum.yok.service.IdariBirimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/idaribirim")
@RequiredArgsConstructor
public class IdariBirimController {

    private final IdariBirimService idariBirimService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid  @RequestBody IdariBirimDTO idariBirimDTO) {
        ApiResponse response = idariBirimService.save(idariBirimDTO);
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(idariBirimService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(idariBirimService.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        idariBirimService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "İdari birim başarıyla silindi.", null));
    }
}
