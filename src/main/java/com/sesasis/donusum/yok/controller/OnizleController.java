package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.service.OnizleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onizle")
@RequiredArgsConstructor
public class OnizleController {

	private final OnizleService onizleService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> onizle() {
		return ResponseEntity.ok(onizleService.onizle());
	}

}
