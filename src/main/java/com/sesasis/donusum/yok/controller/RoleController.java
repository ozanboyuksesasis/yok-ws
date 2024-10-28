package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	@PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody @Valid RoleDTO roleDTO) {
		return ResponseEntity.ok(roleService.save(roleDTO));
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		ApiResponse all = roleService.findAll();
		return ResponseEntity.ok(all);
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
					roleService.deleteById(id);
		return ResponseEntity.ok().build();
	}


}
