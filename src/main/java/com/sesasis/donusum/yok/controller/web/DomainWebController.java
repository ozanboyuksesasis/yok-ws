package com.sesasis.donusum.yok.controller.web;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "/api/web")
@RequiredArgsConstructor
@RestController
public class DomainWebController {

    private final DomainWebService domainWebService;
    private  final AnaBaslikService anaBaslikService;
    private final HaberService haberService;
    private final DuyuruService duyuruService;
    private final OnemliBilgilerService onemliBilgilerService;
    private final GenelDilCategoryService genelDilCategoryService;
    private final DomainService domainService;



    @GetMapping(value = "/domain-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> domainAll() {
        return ResponseEntity.ok(domainService.findAll());
    }

    @GetMapping(value = "/alt-menu-list/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAltMenuOrDomainId(@PathVariable Long domainId){
        ApiResponse listAltMenuOrDomainId = domainWebService.getListAltMenuOrDomainId(domainId);
        return new ResponseEntity<>(listAltMenuOrDomainId,HttpStatus.OK);
    }


    @GetMapping(value = "/get-menu/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMenu(@PathVariable Long domainId) {
        ApiResponse allMenusDomainId = domainWebService.getAllMenusDomainId(domainId);
        return new ResponseEntity<>(allMenusDomainId,HttpStatus.OK);
    }

    @GetMapping(value = "/get-slider/{domainId}")
    public ResponseEntity<?> getSlider(@PathVariable Long domainId) {
        ApiResponse slidersDomainId = domainWebService.getAllSlidersDomainId(domainId);
        return new ResponseEntity<>(slidersDomainId, HttpStatus.OK);
    }


    @GetMapping(value = "/get-baslik/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBaslikDomainId(@PathVariable Long domainId){
        ApiResponse getBaslikDomainId = domainWebService.getBaslikDomainId(domainId);
        return new ResponseEntity<>(getBaslikDomainId,HttpStatus.OK);
    }

    @GetMapping(value = "/get-baslik/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getBaslik(@PathVariable Long id){
        ApiResponse byOneDomainIdAnaBaslik = anaBaslikService.findByOneDomainIdAnaBaslik(id);
        return new ResponseEntity<>(byOneDomainIdAnaBaslik,HttpStatus.OK);
    }
//not : sadece domaindei haberlerin hepsi karışık gelir , dil seçimi için aşağıda var.
    @GetMapping(value = "/get-haber/{domainId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHaber(@PathVariable Long domainId){
        ApiResponse habersDomainId = haberService.getHabersDomainId(domainId);
        return new ResponseEntity<>(habersDomainId,HttpStatus.OK);
    }
    //not : domain ve dil e göre seçim yapan kısm.
    @GetMapping(value = "/get-haber-aktif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHaberAktif(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = haberService.haberListTrueDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping(value = "/get-haber-pasif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHaberPasif(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = haberService.haberListFalseDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/get-duyuru-aktif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDuyuruAktif(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = duyuruService.duyuruListTrueDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/get-duyuru-pasif/{domainId}/{dilCategoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDuyuruPasif(@PathVariable Long domainId ,@PathVariable Long dilCategoryId){
        ApiResponse apiResponse = duyuruService.duyuruListFalseDomainId(domainId, dilCategoryId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }



    @GetMapping(value = "/get-onemli-bilgi-aktif/{domainId}/{dilCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOnemliBilgiAktif(
            @PathVariable Long domainId,
            @PathVariable Long dilCategoryId) {
        ApiResponse apiResponse = onemliBilgilerService.onemliBilgilerListTrueTrueDomainId(domainId, dilCategoryId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "/get-onemli-bilgi-pasif/{domainId}/{dilCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOnemliBilgiPasif(
            @PathVariable Long domainId,
            @PathVariable Long dilCategoryId) {
        ApiResponse apiResponse = onemliBilgilerService.onemliBilgilerFalseTrueDomainId(domainId, dilCategoryId);
        return ResponseEntity.ok(apiResponse);
    }



    @GetMapping(value = "/all-dil-category",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allDilCategory(){
        ApiResponse all = genelDilCategoryService.findAll();
        return ResponseEntity.ok(all);
    }


}
