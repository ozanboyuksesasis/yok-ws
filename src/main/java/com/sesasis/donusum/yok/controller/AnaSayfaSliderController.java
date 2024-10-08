package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.service.AnaSayfaSliderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ana-menu-slider")
@RequiredArgsConstructor
public class AnaSayfaSliderController {

	private final AnaSayfaSliderService anaSayfaSliderService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody AnaSayfaSliderDTO anaSayfaSliderDTO) {
		return ResponseEntity.ok(anaSayfaSliderService.save(anaSayfaSliderDTO));
	}

	@PostMapping(value= MappingConstants.SAVE_WITH_FILE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveWithFile(@RequestPart AnaSayfaSliderDTO model, @RequestPart(value = "files",required = false) MultipartFile[] files, HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(anaSayfaSliderService.saveWithFile(model,files));
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(anaSayfaSliderService.findAll());
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok(anaSayfaSliderService.delete(id));
	}


}
