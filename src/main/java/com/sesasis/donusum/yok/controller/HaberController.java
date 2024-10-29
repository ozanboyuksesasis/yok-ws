package com.sesasis.donusum.yok.controller;


import com.sesasis.donusum.yok.service.HaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/duyuru-haber")
@RequiredArgsConstructor
public class HaberController {

    private final HaberService haberService;


}
