package com.sesasis.donusum.yok.controller.web;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "/api/domain-bilgi")
@RequiredArgsConstructor
@RestController
public class DomainWebController {

    private final DomainWebService domainWebService;
    private  final AnaBaslikService anaBaslikService;
    private final HaberService haberService;
    private final DuyuruService duyuruService;
    private final OnemliBilgilerService onemliBilgilerService;
    private final GenelDilCategoryService genelDilCategoryService;

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
//not : sadece domaindei haberlerin hepsi karışık gelir , dil seçimi için aşağıda var
    @GetMapping(value = "/getHabers/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHabersDomainId(@PathVariable Long domainId){
        ApiResponse habersDomainId = haberService.getHabersDomainId(domainId);
        return new ResponseEntity<>(habersDomainId,HttpStatus.OK);
    }

    @GetMapping(value = "/getDuyurus/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> duyuruListDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = duyuruService.duyuruListDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/getHabers/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> haberListDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = haberService.haberListDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/getOnemliBilgi/{domainId}/{dilCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOnemliBilgi(
            @PathVariable Long domainId,
            @PathVariable Long dilCategoryId) {
        ApiResponse apiResponse = onemliBilgilerService.onemliBilgilerListDomainId(domainId, dilCategoryId);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping(value = "/all-dil-category",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        ApiResponse all = genelDilCategoryService.findAll();
        return ResponseEntity.ok(all);
    }





}
