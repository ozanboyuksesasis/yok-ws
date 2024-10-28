package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.MenuDTO;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuService implements IService<MenuDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final MenuRepository menuRepository;
	private final ModelMapperServiceImpl modelMapperServiceImpl;


	public MenuService(MenuRepository repository, SecurityContextUtil securityContextUtil, MenuRepository menuRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
		this.securityContextUtil = securityContextUtil;
        this.menuRepository = menuRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }


	@Override
	@Transactional
	public ApiResponse save(MenuDTO menuDTO) {

		Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();

		Menu existMenu = menuRepository.findOneByNewDomain_IdAndAnaSayfaMi(loggedDomain.getId(),Boolean.TRUE);

		if (existMenu!=null && menuDTO.isAnaSayfaMi()){
			return new ApiResponse(false, "Sadece bir tane anasayfa tanımlayabilirsiniz.", null);
		}
//kayıt edilecek.

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		List<MenuDTO> menuDTOList = menuRepository.findAllByNewDomain_Id(domainId)
				.stream()
				.map(menu -> modelMapperServiceImpl.response().map(menu, MenuDTO.class))
				.collect(Collectors.toList());

		return new ApiResponse(true, MessageConstant.SUCCESS, menuDTOList);
	}


	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	public ApiResponse findAllWithoutAnasayfa() {
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		List<MenuDTO> menuDTOList = menuRepository
				.findAllByNewDomain_IdAndAnaSayfaMi(domainId, Boolean.FALSE)
				.stream()
				.map(menu -> modelMapperServiceImpl.response().map(menu, MenuDTO.class))
				.collect(Collectors.toList());

		return new ApiResponse(true, MessageConstant.SUCCESS, menuDTOList);
	}



	public ApiResponse findDomainAnasayfa() {
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		Menu anasayfa = menuRepository.findOneByNewDomain_IdAndAnaSayfaMi(domainId, Boolean.TRUE);

		if (anasayfa == null) {
			return new ApiResponse(false, MessageConstant.ERROR, null);
		}

		MenuDTO anasayfaDTO = modelMapperServiceImpl.response().map(anasayfa, MenuDTO.class);
		return new ApiResponse(true, MessageConstant.SUCCESS, anasayfaDTO);
	}


}
