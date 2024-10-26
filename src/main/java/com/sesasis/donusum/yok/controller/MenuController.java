package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody MenuDTO menuDTO) {
		return ResponseEntity.ok(menuService.save(menuDTO));
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(menuService.findAll());
	}

	@GetMapping(value = "/all-without-ana-sayfa", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAllWithoutAnasayfa() {
		return ResponseEntity.ok(menuService.findAllWithoutAnasayfa());
	}

	@GetMapping(value = "/find-domain-anasayfa", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findDomainAnasayfa() {
		return ResponseEntity.ok(menuService.findDomainAnasayfa());
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		 menuService.deleteById(id);
		return ResponseEntity.ok().build();
	}


}
