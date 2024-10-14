package com.sesasis.donusum.yok.controller;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.service.RoleDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/role-dashboard")
public class RoleDashboardController {

    private final RoleDashboardService roleDashboardService;


    public RoleDashboardController(RoleDashboardService roleDashboardService) {
        this.roleDashboardService = roleDashboardService;
    }


    @PostMapping("/{roleId}/assign-menus")
    public ResponseEntity<ApiResponse> assignDashboardMenusToRole(@PathVariable Long roleId,  @RequestBody List<Long> dashboardMenuIds) {
        ApiResponse response = roleDashboardService.assignDashboardListToRole(roleId, dashboardMenuIds);
        return ResponseEntity.ok(response);
    }
}
