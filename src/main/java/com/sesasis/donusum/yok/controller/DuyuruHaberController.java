package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.service.DuyuruHaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/duyuru-haber")
@RequiredArgsConstructor
public class DuyuruHaberController {

    private final DuyuruHaberService duyuruHaberService;


}
