package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.NewAltMenuDTO;
import com.sesasis.donusum.yok.service.NewAltMenuService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/new-altmenu")
@RequiredArgsConstructor
public class NewAltMenuController {


    private  final NewAltMenuService newAltMenuService;

    @PostMapping(value = "/save-list/{altMenuGroupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody List<NewAltMenuDTO> newAltMenuDTO, @PathVariable Long altMenuGroupId){
        ApiResponse apiResponse = newAltMenuService.addListNewAltMenu(newAltMenuDTO, altMenuGroupId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
