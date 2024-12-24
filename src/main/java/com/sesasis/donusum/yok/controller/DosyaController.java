package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DosyaDTO;
import com.sesasis.donusum.yok.service.DosyaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/dosya")
@RequiredArgsConstructor
public class DosyaController {

    private final DosyaService dosyaService;


    @PostMapping(value = "/save-file",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveFile(@RequestPart List<DosyaDTO> dosyaDTO, @RequestPart(value = "file",required = false) MultipartFile file, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = dosyaService.saveDosya(dosyaDTO, file);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        ApiResponse all = dosyaService.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }


}
