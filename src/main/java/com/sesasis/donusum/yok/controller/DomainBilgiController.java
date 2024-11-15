package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.DomainBilgiService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/domain-bilgi")
@RequiredArgsConstructor
@RestController
public class DomainBilgiController {

    private final DomainBilgiService domainBilgiService;


    @GetMapping(value = "/getMenu/{domainId}")
    public ResponseEntity<?> getDomainIdMenu(@PathVariable Long domainId) {
        ApiResponse domainIdMenu = domainBilgiService.getAllMenusDomainId(domainId);
        return new ResponseEntity<>(domainIdMenu, HttpStatus.OK);
    }


}
