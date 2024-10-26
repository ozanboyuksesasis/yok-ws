package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.dto.DomainDTO;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/domain")
@RequiredArgsConstructor
public class DomainController {

	private final DomainService domainService;

	@PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody DomainDTO domainDTO) {
		return ResponseEntity.ok(domainService.save(domainDTO));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable Long id) {
		return ResponseEntity.ok(domainService.findById(id));
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		domainService.deleteById(id);
		return ResponseEntity.ok().build();
	}


}
