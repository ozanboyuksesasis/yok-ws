package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DomainLogoDTO;
import com.sesasis.donusum.yok.service.DomainLogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/domain-logos")
public class DomainLogoController {

    private final DomainLogoService domainLogoService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody DomainLogoDTO domainLogoDTO) {
        ApiResponse response = domainLogoService.save(domainLogoDTO);
        HttpStatus status = response.getSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        ApiResponse response = domainLogoService.findAll();
        return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        ApiResponse response = domainLogoService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        domainLogoService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Silme işlemi başarılı.", null), HttpStatus.OK);
    }
}
