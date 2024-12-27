package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.service.AnaSayfaSliderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


	@PostMapping(value= MappingConstants.SAVE_WITH_FILE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveWithFile(@RequestPart AnaSayfaSliderDTO model, @RequestPart(value = "files", required = false) MultipartFile[] files, HttpServletRequest httpServletRequest) {
    if (model.getGenelDilCategoryId() == null) {
        return ResponseEntity.badRequest().body("GenelDilCategory ID must not be null");
    }
    return ResponseEntity.ok(anaSayfaSliderService.saveWithFile(model, files));
}


	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(anaSayfaSliderService.findAll());
	}

	@DeleteMapping(MappingConstants.PATH_VARIABLE_ID)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		anaSayfaSliderService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping(value = "/delete-by-sira-no/{siraNo}")
	public ResponseEntity<?> deleteBySiraNo(@PathVariable Long siraNo) {
		anaSayfaSliderService.deleteBySiraNo(siraNo);
		return ResponseEntity.ok(new ApiResponse(true, "Slider silme işlemi başarılı oldu.", null));
	}
	@PutMapping(value = "/update-sira-no/{id}/{newSiraNo}")
	public ResponseEntity<?> updateSiraNo(@PathVariable Long id, @PathVariable Long newSiraNo) {
		ApiResponse apiResponse = anaSayfaSliderService.updateSiraNo(id, newSiraNo);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
