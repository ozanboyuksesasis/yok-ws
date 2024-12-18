package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.service.MenuIcerikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-icerik")
@RequiredArgsConstructor
public class MenuIcerikController {

    private final MenuIcerikService menuIcerikService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody MenuIcerikDTO menuIcerikDTO) {
        return ResponseEntity.ok(menuIcerikService.save(menuIcerikDTO));
    }

    @PostMapping(value = "/list-save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody List<MenuIcerikDTO> menuIcerikDTO) {
        ApiResponse apiResponse = menuIcerikService.addListIcerik(menuIcerikDTO);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(menuIcerikService.findAll());
    }

    @DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(menuIcerikService.delete(id));
    }

    @GetMapping(value = "/by-alt-menu-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getIcerikByAltMenuUrl(@RequestParam String altMenuUrl) {
        return ResponseEntity.ok(menuIcerikService.getIcerikByAltMenuUrl(altMenuUrl));
    }

    @GetMapping(value = "/get-alt-menü-icerik",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAltMenuIcerik(){
        ApiResponse listAltMenuIcerik = menuIcerikService.getListAltMenuIcerik();
        return new ResponseEntity<>(listAltMenuIcerik, HttpStatus.OK);
    }

}
