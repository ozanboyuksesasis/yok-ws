package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.GorevDonemiDTO;
import com.sesasis.donusum.yok.service.GorevDonemiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gorev-donemi")
@RequiredArgsConstructor
public class GorevDonemiController {
    private final GorevDonemiService gorevDonemiService;


    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(gorevDonemiService.findAll());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ApiResponse apiResponse = gorevDonemiService.findById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping(value = "/updateTarih/{gorevDonemiID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTarih(@PathVariable Long gorevDonemiID, @RequestBody GorevDonemiDTO gorevDonemiDTO) {
        ApiResponse apiResponse = gorevDonemiService.updateTarih(gorevDonemiID, gorevDonemiDTO);
        if (apiResponse.getSuccess()) {
            return ResponseEntity.ok(apiResponse);
        } else {
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}

