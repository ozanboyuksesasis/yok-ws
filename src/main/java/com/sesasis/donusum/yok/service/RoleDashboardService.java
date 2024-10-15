package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.repository.DashBoardMenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleDashboardService {

    private final RoleRepository roleRepository;
    private final DashBoardMenuRepository dashboardMenuRepository;


    public RoleDashboardService(RoleRepository roleRepository, DashBoardMenuRepository dashboardMenuRepository) {
        this.roleRepository = roleRepository;
        this.dashboardMenuRepository = dashboardMenuRepository;
    }

    public ApiResponse assignDashboardListToRole(Long roleId, List<Long> dashboardMenuIds) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (!roleOptional.isPresent()) {
            return new ApiResponse(false, "Role not found", null);
        }

        Role role = roleOptional.get();

        List<DashboardMenu> dashboardMenus = dashboardMenuRepository.findAllById(dashboardMenuIds);

        role.getDashboardMenuList().addAll(dashboardMenus);
        roleRepository.save(role);

        return new ApiResponse(true, "Dashboard menus successfully assigned to role", role.toDTO());
    }

}
