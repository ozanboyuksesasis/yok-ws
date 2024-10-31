package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.SliderDTO;
import com.sesasis.donusum.yok.entity.Slider;
import com.sesasis.donusum.yok.service.SliderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sliders")
public class SliderController {

    private  final SliderService sliderService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SliderDTO sliderDTO) {
        ApiResponse save = sliderService.save(sliderDTO);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @PostMapping(value = "/{newDomainId}/{sliderId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> DomainEkle(@PathVariable Long  newDomainId,@PathVariable Long sliderId) {
        ApiResponse response = sliderService.domainEkle(newDomainId, sliderId);
        return new ResponseEntity<>(response, HttpStatus.OK );
    }
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        ApiResponse all = sliderService.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApiResponse byId = sliderService.findById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        sliderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
