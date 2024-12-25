package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody MenuDTO menuDTO) {
		return ResponseEntity.ok(menuService.save(menuDTO));
	}

	@PostMapping(value = "/list-save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody List<MenuDTO> menuDTO) {
		ApiResponse apiResponse = menuService.saveList(menuDTO);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}

	@PostMapping(value = "/update-save/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody List<MenuDTO> menuDTO,@PathVariable Long groupId) {
		ApiResponse apiResponse = menuService.updateMenu(menuDTO,groupId);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
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

	@DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		menuService.deleteById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Menü başarıyla silindi.", null));
	}


}
