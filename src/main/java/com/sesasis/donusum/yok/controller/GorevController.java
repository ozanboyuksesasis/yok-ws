package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.GorevDTO;
import com.sesasis.donusum.yok.service.GorevService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/gorev")
@RequiredArgsConstructor
public class GorevController {

    private final GorevService gorevService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody GorevDTO gorevDTO){
        ApiResponse save = gorevService.save(gorevDTO);
        return ResponseEntity.ok(save);
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id){
        ApiResponse byId = gorevService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(){
        ApiResponse all = gorevService.findAll();
        return ResponseEntity.ok(all);
    }
    @DeleteMapping(value = MappingConstants.PATH_VARIABLE_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        gorevService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/personeller/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> gorevVePersoneller(@PathVariable Long id) {
        ApiResponse response = gorevService.gorevVePersoneller(id);
        return ResponseEntity.ok(response);
    }







}
