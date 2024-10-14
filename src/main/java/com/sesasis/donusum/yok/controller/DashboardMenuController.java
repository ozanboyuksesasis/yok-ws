package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.dto.DashboardMenuDTO;
import com.sesasis.donusum.yok.service.DashBoardMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard-menu")
@RequiredArgsConstructor
public class DashboardMenuController {

    private final DashBoardMenuService dashboardMenuService;


    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@RequestBody DashboardMenuDTO dashboardMenuDTO) {
        return ResponseEntity.ok(dashboardMenuService.save(dashboardMenuDTO));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(dashboardMenuService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        dashboardMenuService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, MessageConstant.DELETE_MSG, null));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardMenuService.findById(id));
    }
}
