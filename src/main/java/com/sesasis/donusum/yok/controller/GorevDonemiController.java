package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.GorevDonemiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gorev-donemi")
@RequiredArgsConstructor
public class GorevDonemiController {
    private final GorevDonemiService gorevDonemiService;



@GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(){
    return ResponseEntity.ok(gorevDonemiService.findAll());
}

@GetMapping(value ="{id}",produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> findById(@PathVariable Long id){
          ApiResponse apiResponse = gorevDonemiService.findById(id);
    return  ResponseEntity.ok(apiResponse);
        }
}
