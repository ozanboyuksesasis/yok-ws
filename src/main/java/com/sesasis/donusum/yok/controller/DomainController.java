package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DomainDTO;
import com.sesasis.donusum.yok.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(domainService.findAll());
	}

	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getId(@PathVariable Long id){
		ApiResponse byId = domainService.findById(id);
		return new ResponseEntity<>(byId,HttpStatus.OK);
	}
	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok(domainService.delete(id));
	}

}
