package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuIcerikDTO;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.MenuIcerikRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuIcerikService implements IService<MenuIcerikDTO> {

	private final SecurityContextUtil securityContextUtil;
    private final MenuIcerikRepository menuIcerikRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;

	public MenuIcerikService(MenuIcerikRepository repository, SecurityContextUtil securityContextUtil, MenuIcerikRepository menuIcerikRepository, ModelMapperServiceImpl modelMapperServiceImpl) {

		this.securityContextUtil = securityContextUtil;
        this.menuIcerikRepository = menuIcerikRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }


	@Override
	@Transactional
	public ApiResponse save(MenuIcerikDTO menuIcerikDTO) {
		MenuIcerik menuIcerik = this.modelMapperServiceImpl.request().map(menuIcerikDTO, MenuIcerik.class);
        menuIcerikRepository.save(menuIcerik);
        return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
    public ApiResponse findAll() {
        Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
        List<MenuIcerikDTO> menuIcerikDTOList = menuIcerikRepository
                .findAllByAltMenuAnaMenuNewDomainId(domainId)
                .stream()
                .map(menuIcerik -> modelMapperServiceImpl.response().map(menuIcerik, MenuIcerikDTO.class)).collect(Collectors.toList());

        return new ApiResponse(true, MessageConstant.SUCCESS, menuIcerikDTOList);
    }

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

    public ApiResponse getIcerikByAltMenuUrl(String altMenuUrl) {
        Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
        MenuIcerik menuIcerik = menuIcerikRepository.findOneByAltMenuAnaMenuNewDomainIdAndAltMenuUrl(domainId, altMenuUrl);

        if (menuIcerik == null) {
            return new ApiResponse(false, "Menü bulunamadı.", null);
        }

        MenuIcerikDTO menuIcerikDTO = modelMapperServiceImpl.response().map(menuIcerik, MenuIcerikDTO.class);
        return new ApiResponse(true, MessageConstant.SUCCESS, menuIcerikDTO);
    }

}
