package com.sesasis.donusum.yok.controller.web;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.AnaBaslikService;
import com.sesasis.donusum.yok.service.DomainWebService;
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

    private final DomainWebService domainWebService;
    private  final AnaBaslikService anaBaslikService;


    @GetMapping(value = "/getMenus/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMenusDomainId(@PathVariable Long domainId) {
        ApiResponse allMenusDomainId = domainWebService.getAllMenusDomainId(domainId);
        return new ResponseEntity<>(allMenusDomainId,HttpStatus.OK);
    }

    @GetMapping(value = "/getSliders/{domainId}")
    public ResponseEntity<?> getAllSlidersDomainId(@PathVariable Long domainId) {
        ApiResponse slidersDomainId = domainWebService.getAllSlidersDomainId(domainId);
        return new ResponseEntity<>(slidersDomainId, HttpStatus.OK);
    }


    @GetMapping(value = "/getBaslik/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allBaslikDomainId(@PathVariable Long domainId){
        ApiResponse getBaslikDomainId = domainWebService.getBaslikDomainId(domainId);
        return new ResponseEntity<>(getBaslikDomainId,HttpStatus.OK);
    }
    @GetMapping(value = "/getBaslik/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getAnaBaslik(@PathVariable Long id){
        ApiResponse byOneDomainIdAnaBaslik = anaBaslikService.findByOneDomainIdAnaBaslik(id);
        return new ResponseEntity<>(byOneDomainIdAnaBaslik,HttpStatus.OK);
    }





}
