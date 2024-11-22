package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.AnaBaslikDTO;
import com.sesasis.donusum.yok.service.AnaBaslikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/ana-baslik")
@RestController
public class AnaBaslikController {

    private  final AnaBaslikService anaBaslikService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody AnaBaslikDTO anaBaslikDTO){
        ApiResponse save = anaBaslikService.save(anaBaslikDTO);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> findById(@PathVariable Long id){
        ApiResponse byId = anaBaslikService.findById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        anaBaslikService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse<>(true,"Silme işlemi başarılı.",null)
                ,HttpStatus.OK);
    }



}
