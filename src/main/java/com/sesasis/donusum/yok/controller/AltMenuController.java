package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import com.sesasis.donusum.yok.service.AltMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alt-menu")
@RequiredArgsConstructor
public class AltMenuController {

    private final AltMenuService altMenuService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody AltMenuDTO altMenuDTO) {
        return ResponseEntity.ok(altMenuService.save(altMenuDTO));
    }

    @PostMapping(value = "/list-save/{menuGroupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody List<AltMenuDTO> altMenuDTOS, @PathVariable Long menuGroupId) {
        ApiResponse apiResponse = altMenuService.addListAltMenu(altMenuDTOS, menuGroupId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(altMenuService.findAll());
    }

    @DeleteMapping(value = "/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long groupId) {
        altMenuService.deleteById(groupId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Silme başarılı.", null));
    }


}
