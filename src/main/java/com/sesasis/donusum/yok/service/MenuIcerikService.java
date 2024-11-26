package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import com.sesasis.donusum.yok.repository.MenuIcerikRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class MenuIcerikService extends AbstractService<MenuIcerik, MenuIcerikRepository> implements IService<MenuIcerikDTO> {

    private final SecurityContextUtil securityContextUtil;

    public MenuIcerikService(MenuIcerikRepository repository, SecurityContextUtil securityContextUtil) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
    }


    @Override
    @Transactional
    public ApiResponse save(MenuIcerikDTO menuIcerikDTO) {

        MenuIcerik menuIcerik = menuIcerikDTO.toEntity();
        getRepository().save(menuIcerik);

        return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
    }

    @Override
    public ApiResponse findAll() {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findAllByAltMenuAnaMenuDomainId(loggedDomain.getId()).stream().map(e->e.toDTO()).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public ApiResponse getIcerikByAltMenuUrl(String altMenuUrl) {
        Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
        if (loggedDomain == null) {
            return new ApiResponse(false, "No domain context available", null);
        }
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findOneByAltMenuAnaMenuDomainIdAndAltMenuUrl(loggedDomain.getId(), altMenuUrl).toDTO());
    }
}
