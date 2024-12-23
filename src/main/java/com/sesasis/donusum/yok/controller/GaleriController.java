package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.GaleriDTO;
import com.sesasis.donusum.yok.service.GaleriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/galeri")
@RequiredArgsConstructor
public class GaleriController {

    private final GaleriService galeriService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody GaleriDTO galeriDTO){
        ApiResponse save = galeriService.save(galeriDTO);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        ApiResponse all = galeriService.findAll();
    return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        galeriService.deleteById(id);
        return  ResponseEntity.ok(new ApiResponse<>(true,"Silme başarılı.",null));
    }

}
