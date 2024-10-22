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

@RestController
@RequestMapping("/api/gorev")
@RequiredArgsConstructor
public class GorevController {

    private final GorevService service;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody GorevDTO gorevDTO){
        ApiResponse save = service.save(gorevDTO);
        return ResponseEntity.ok(save);
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id){
        ApiResponse byId = service.findById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(){
        ApiResponse all = service.findAll();
        return ResponseEntity.ok(all);
    }
    @DeleteMapping(value = MappingConstants.PATH_VARIABLE_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }







}
