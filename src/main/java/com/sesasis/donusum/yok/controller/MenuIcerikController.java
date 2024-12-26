package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.constant.MappingConstants;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.service.MenuIcerikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-icerik")
@RequiredArgsConstructor
public class MenuIcerikController {

    private final MenuIcerikService menuIcerikService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody MenuIcerikDTO menuIcerikDTO) {
        return ResponseEntity.ok(menuIcerikService.save(menuIcerikDTO));
    }

    @PostMapping(value = {"/list-save/{menuGroupId}", "/list-save/{menuGroupId}/{altMenuGroupId}", "/list-save/{menuGroupId}/{altMenuGroupId}/{newAltMenuGroupId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @RequestBody List<MenuIcerikDTO> menuIcerikDTO,
            @PathVariable(required = false) Long menuGroupId,
            @PathVariable(required = false) Long altMenuGroupId,
            @PathVariable(required = false) Long newAltMenuGroupId) {
        ApiResponse apiResponse = menuIcerikService.addListIcerik(menuIcerikDTO, menuGroupId, altMenuGroupId, newAltMenuGroupId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(menuIcerikService.findAll());
    }

    @DeleteMapping(value = "/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable Long groupId) {
       menuIcerikService.deleteById(groupId);
       return ResponseEntity.ok(new ApiResponse<>(true,"Silme başarılı.",null));
    }

    @GetMapping(value = "/by-alt-menu-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getIcerikByAltMenuUrl(@RequestParam String altMenuUrl) {
        return ResponseEntity.ok(menuIcerikService.getIcerikByAltMenuUrl(altMenuUrl));
    }



}
