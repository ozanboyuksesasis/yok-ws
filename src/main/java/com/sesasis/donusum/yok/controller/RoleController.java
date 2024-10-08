package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	@PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody RoleDTO roleDTO) {
		return ResponseEntity.ok(roleService.save(roleDTO));
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(roleService.findAll());
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok(roleService.delete(id));
	}


}
