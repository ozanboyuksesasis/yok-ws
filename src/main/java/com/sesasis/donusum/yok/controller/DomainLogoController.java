package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.service.DomainLogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/domain-logo")
@RequiredArgsConstructor
public class DomainLogoController {

    private final DomainLogoService domainLogoService;
}
