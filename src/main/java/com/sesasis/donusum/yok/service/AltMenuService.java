package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AltMenuService implements IService<AltMenuDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final AltMenuRepository altMenuRepository;
	private final ModelMapperServiceImpl modelMapperServiceImpl;

	public AltMenuService(SecurityContextUtil securityContextUtil, AltMenuRepository altMenuRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
		this.securityContextUtil = securityContextUtil;
		this.altMenuRepository = altMenuRepository;
		this.modelMapperServiceImpl = modelMapperServiceImpl;
	}

	@Override
	@Transactional
	public ApiResponse save(AltMenuDTO altMenuDTO) {
		AltMenu altMenu = modelMapperServiceImpl.request().map(altMenuDTO, AltMenu.class);
		altMenuRepository.save(altMenu);
		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		List<AltMenuDTO> altMenuDTOList = altMenuRepository
				.findAllByAnaMenuDomainId(domainId)
				.stream()
				.map(altMenu -> modelMapperServiceImpl.response().map(altMenu, AltMenuDTO.class))
				.collect(Collectors.toList());

		return new ApiResponse(true, MessageConstant.SUCCESS, altMenuDTOList);
	}

	@Override
	public ApiResponse findById(Long id) {
		AltMenu altMenu = altMenuRepository.findById(id).orElse(null);
		if (altMenu == null) {
			return new ApiResponse(false,"Altmenü bulunamadı.", null);
		}

		AltMenuDTO altMenuDTO = modelMapperServiceImpl.response().map(altMenu, AltMenuDTO.class);
		return new ApiResponse(true, MessageConstant.SUCCESS, altMenuDTO);
	}

	@Override
	public void deleteById(Long id) {
		altMenuRepository.deleteById(id);
	}
}
