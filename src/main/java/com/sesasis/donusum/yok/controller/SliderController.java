package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.entity.Slider;
import com.sesasis.donusum.yok.service.SliderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/sliders")
public class SliderController {

    private  final SliderService sliderService;

}
