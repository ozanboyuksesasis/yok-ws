package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.FotografDTO;
import com.sesasis.donusum.yok.service.FotografService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fotografs")
public class FotografController {

    private final FotografService fotografService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody FotografDTO fotografDTO) {
        ApiResponse response = fotografService.save(fotografDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        ApiResponse response = fotografService.findAll();
        HttpStatus status = response.getSuccess()  ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        ApiResponse response = fotografService.findById(id);
        HttpStatus status = response.getSuccess()  ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        fotografService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Silme işlemi başarılı.", null), HttpStatus.OK);
    }

    @PutMapping(value = "/sira-no-guncelle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> siraNoGuncelle() {
        ApiResponse response = fotografService.siraNoGuncelle();
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping(value = "/{sliderId}/{fotografId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> sliderNoEkle(@PathVariable Long sliderId, @PathVariable Long fotografId) {
        ApiResponse response = fotografService.sliderNoEkle(sliderId, fotografId);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }
}
