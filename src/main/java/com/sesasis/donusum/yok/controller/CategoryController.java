package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(name = "api/categories")
public class CategoryController {

    private final CategoryService categoryService;



}