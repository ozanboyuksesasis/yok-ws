package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.models.User;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.security.repository.UserRepository;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DashboardMenuDTO;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.repository.DashBoardMenuRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashBoardMenuService implements IService<DashboardMenuDTO> {
    private final RoleRepository roleRepository;

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final DashBoardMenuRepository dashboardMenuRepository;

    public DashBoardMenuService(RoleRepository roleRepository, MenuRepository menuRepository, UserRepository userRepository, DashBoardMenuRepository dashboardMenuRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.dashboardMenuRepository = dashboardMenuRepository;
    }

    @Override
    public ApiResponse save(DashboardMenuDTO dashboardMenuDTO) {
        DashboardMenu dashboardMenu = dashboardMenuDTO.toEntity();
        if (dashboardMenuRepository.existsByName(dashboardMenuDTO.getName())) {
            return new ApiResponse(false, MessageConstant.ALREADY_EXISTS, dashboardMenuDTO.getName());
        }
        dashboardMenuRepository.save(dashboardMenu);
        return new ApiResponse<>(true, MessageConstant.SAVE_MSG,null);
    }

    @Override
    public ApiResponse findAll() {
        List<DashboardMenu> dashboardMenus = dashboardMenuRepository.findAll();
        if (dashboardMenus.isEmpty()) {
            return new ApiResponse(false, MessageConstant.SUCCESS_NOT_LIST, null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS_LIST, dashboardMenus);
    }

    @Override
    public ApiResponse findById(Long id) {
        Optional<DashboardMenu> dashboardMenu = dashboardMenuRepository.findById(id);
        if (dashboardMenu.isPresent()) {
            return new ApiResponse(true, MessageConstant.GET_BY_ID_SUCCESS, dashboardMenu.get());
        }
        return new ApiResponse(false, MessageConstant.GET_BY_ID_ERROR, null);
    }


    @Override
    public void deleteById(Long id) {
        if (dashboardMenuRepository.existsById(id)) {
            dashboardMenuRepository.deleteById(id);
        } else {
            throw new RuntimeException(MessageConstant.GET_BY_ID_ERROR);
        }
    }

}

