package com.sesasis.donusum.yok.controller.web;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.DomainBilgiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/domain-bilgi")
@RequiredArgsConstructor
@RestController
public class DomainWebController {

    private final DomainBilgiService domainBilgiService;


    @GetMapping(value = "/getMenus/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMenusDomainId(@PathVariable Long domainId) {
        ApiResponse domainIdMenu = domainBilgiService.getAllMenusDomainId(domainId);
        return new ResponseEntity<>(domainIdMenu, HttpStatus.OK);
    }

    @GetMapping(value = "/getSliders/{domainId}")
    public ResponseEntity<?> getAllSlidersDomainId(@PathVariable Long domainId) {
        ApiResponse slidersDomainId = domainBilgiService.getAllSlidersDomainId(domainId);
        return new ResponseEntity<>(slidersDomainId, HttpStatus.OK);
    }





}
