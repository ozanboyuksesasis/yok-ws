package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class AltMenuService extends AbstractService<AltMenu, AltMenuRepository> implements IService<AltMenuDTO> {

	private final SecurityContextUtil securityContextUtil;

	public AltMenuService(AltMenuRepository repository, SecurityContextUtil securityContextUtil) {
		super(repository);
		this.securityContextUtil = securityContextUtil;
	}


	@Override
	@Transactional
	public ApiResponse save(AltMenuDTO altMenuDTO) {
		getRepository().save(altMenuDTO.toEntity());
		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		return new ApiResponse(true,MessageConstant.SUCCESS,getRepository().findAllByAnaMenuDomainId(securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e->e.toDTO()).collect(Collectors.toList()));
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
