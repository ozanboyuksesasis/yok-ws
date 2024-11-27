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
//not : sadece domaindei haberlerin hepsi karışık gelir , dil seçimi için aşağıda var.
    @GetMapping(value = "/getHabers/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHabersDomainId(@PathVariable Long domainId){
        ApiResponse habersDomainId = haberService.getHabersDomainId(domainId);
        return new ResponseEntity<>(habersDomainId,HttpStatus.OK);
    }
    //not : domain ve dil e göre seçim yapan kısm.
    @GetMapping(value = "/get-haber-aktif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> haberListAktifDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = haberService.haberListTrueDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping(value = "/get-haber-pasif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> haberListPasifDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = haberService.haberListFalseDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/get-duyuru-aktif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> duyuruListDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = duyuruService.duyuruListTrueDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/get-duyuru-pasif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> duyuruListFalseDomainId(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = duyuruService.duyuruListFalseDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }



    @GetMapping(value = "/get-onemli-bilgi-aktif/{domainId}/{dilCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOnemliAktifBilgi(
            @PathVariable Long domainId,
            @PathVariable Long dilCategoryId) {
        ApiResponse apiResponse = onemliBilgilerService.onemliBilgilerListTrueTrueDomainId(domainId, dilCategoryId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "/get-onemli-bilgi-pasif/{domainId}/{dilCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOnemliPasifBilgi(
            @PathVariable Long domainId,
            @PathVariable Long dilCategoryId) {
        ApiResponse apiResponse = onemliBilgilerService.onemliBilgilerFalseTrueDomainId(domainId, dilCategoryId);
        return ResponseEntity.ok(apiResponse);
    }



    @GetMapping(value = "/all-dil-category",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        ApiResponse all = genelDilCategoryService.findAll();
        return ResponseEntity.ok(all);
    }


}
