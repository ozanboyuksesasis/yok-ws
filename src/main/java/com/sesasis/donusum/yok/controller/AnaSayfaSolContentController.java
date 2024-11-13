package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.dto.AnaSayfaSolContentDTO;
import com.sesasis.donusum.yok.service.AnaSayfaSolContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ana-sayfa-sol-content")
@RequiredArgsConstructor
public class AnaSayfaSolContentController {

	private final AnaSayfaSolContentService anaSayfaSolContentService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody AnaSayfaSolContentDTO anaSayfaSolContentDTO) {
		return ResponseEntity.ok(anaSayfaSolContentService.save(anaSayfaSolContentDTO));
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(anaSayfaSolContentService.findAll());
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok(anaSayfaSolContentService.delete(id));
	}


}
