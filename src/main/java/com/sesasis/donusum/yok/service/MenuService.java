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
import com.sesasis.donusum.yok.repository.DomainRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService extends AbstractService<Menu, MenuRepository> implements IService<MenuDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final DomainRepository domainRepository;
	private final ModelMapperServiceImpl modelMapperServiceImpl;

	public MenuService(MenuRepository repository, SecurityContextUtil securityContextUtil, DomainRepository domainRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
		super(repository);
		this.securityContextUtil = securityContextUtil;
        this.domainRepository = domainRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }


	@Override
	@Transactional
	public ApiResponse save(MenuDTO menuDTO) {

		List<Domain> loggedDomain = domainRepository.findAll();

		Domain domain = this.domainRepository.findById(menuDTO.getDomainId()).orElse(null);
		if (domain==null){
			return new ApiResponse<>(false,"Domain bulunamadı.",null);
		}

		Menu existMenu = getRepository().findOneByDomainIdAndAnaSayfaMi(loggedDomain.getId(),Boolean.TRUE);

		if (existMenu != null){
			return new ApiResponse(false, "Hata : Sadece bir tane ana menü olabilir.", null);
		}

		Menu menu = menuDTO.toEntity();
		menu.setDomain(domain);
		getRepository().save(menu);

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {

		Domain loggedDomain = securityContextUtil.getCurrentUser().getLoggedDomain();
		if (loggedDomain == null){
			return new ApiResponse<>(false,"Kullanıcı menüleri bulunamadı.",null);
		}
		List<Menu> menus = loggedDomain.getMenuList();
		if (menus.isEmpty() || menus == null){
			return new ApiResponse<>(false,"Menü listesi boş.",null);
		}
		List<MenuDTO> dtos = menus.stream().map(menu -> this.modelMapperServiceImpl.response().map(menu, MenuDTO.class)).collect(Collectors.toList());
		return new ApiResponse<>(true,"İşlem başarılı.",dtos);
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	public ApiResponse findAllWithoutAnasayfa() {
		return new ApiResponse(true,MessageConstant.SUCCESS,getRepository().findAllByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),Boolean.FALSE).stream().map(e->e.toDTO()).collect(Collectors.toList()));
	}

	public ApiResponse findDomainAnasayfa() {
		Menu anasayfa = getRepository().findOneByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),Boolean.TRUE);

		if (anasayfa == null){
			return new ApiResponse(false,MessageConstant.ERROR,null);
		}
		return new ApiResponse(true,MessageConstant.SUCCESS,getRepository().findOneByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),Boolean.TRUE).toDTO());
	}

}
