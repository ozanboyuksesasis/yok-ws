package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.*;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DomainService extends AbstractService<Domain, DomainRepository> implements IService<DomainDTO> {

	private  final RoleRepository roleRepository;
	private final DomainRepository domainRepository;
	private final ModelMapperServiceImpl modelMapperServiceImpl;
	public DomainService(DomainRepository repository, RoleRepository roleRepository, DomainRepository domainRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
		super(repository);
		this.roleRepository = roleRepository;
        this.domainRepository = domainRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }


	@Override
	@Transactional
	public ApiResponse save(DomainDTO domainDTO) {

		if (getRepository().existsByAd(domainDTO.getAd())) {
			return new ApiResponse(false, "Domain adı zaten kullanılıyor.", domainDTO.getAd());
		}
		Domain domain =	getRepository().save(domainDTO.toEntity());


		if (domainDTO.isAnaDomainMi()){//ana domain menüleri farklı olacak
			DashboardMenu anaSayfaSliderEkle = new DashboardMenu();
			anaSayfaSliderEkle.setId(9L);


		}else{
			DashboardMenu tanimlamalar = new DashboardMenu();
			tanimlamalar.setId(1L);
			DashboardMenu menuOlustur = new DashboardMenu();
			menuOlustur.setId(5L);
			DashboardMenu altMenuOlustur = new DashboardMenu();
			altMenuOlustur.setId(6L);
			DashboardMenu anaSayfaMenuIcerik = new DashboardMenu();
			anaSayfaMenuIcerik.setId(7L);
			DashboardMenu altMenuIcerik = new DashboardMenu();
			altMenuIcerik.setId(8L);

			List<DashboardMenu> dashboardMenuList = new ArrayList<>();
			dashboardMenuList.add(tanimlamalar);
			dashboardMenuList.add(menuOlustur);
			dashboardMenuList.add(altMenuOlustur);
			dashboardMenuList.add(anaSayfaMenuIcerik);
			dashboardMenuList.add(altMenuIcerik);
			Role role = roleRepository.findById(domainDTO.getRole().getId()).get();
			role.setDashboardMenuList(dashboardMenuList);
			roleRepository.save(role);

			List<Domain> list = role.getDomains();
			list.add(domain);
			role.setDomains(list);
		}

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		List<Domain> domains = this.domainRepository.findAll();
		if (domains == null) {
			return new ApiResponse<>(false, "Domain listesi boş.", null);
		}
			List<DomainDTO> dtos = domains.stream().map(domain -> {
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setId(domainDTO.getId());
			domainDTO.setAd(domain.getAd());
			domainDTO.setAnaDomainMi(domain.isAnaDomainMi());
			domainDTO.setRole(domain.getRole().toDTO());
			domainDTO.setDeleted(domain.getDeleted());
			domainDTO.setUrl(domain.getUrl());

			domainDTO.setSliders(domain.getSliders().stream().map(slider -> this.modelMapperServiceImpl.response().map(slider, SliderDTO.class)).collect(Collectors.toList()));
			domainDTO.setDuyuruList(domain.getDuyuruList().stream().map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru, DuyuruDTO.class)).collect(Collectors.toList()));
			domainDTO.setHaberList(domain.getHaberList().stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));
			domainDTO.setMenuList(domain.getMenuList().stream().map(menu -> this.modelMapperServiceImpl.response().map(menu, MenuDTO.class)).collect(Collectors.toList()));
			domainDTO.setDomainLogos(domain.getDomainLogos().stream().map(domainLogo -> this.modelMapperServiceImpl.response().map(domainLogo, DomainLogoDTO.class)).collect(Collectors.toList()));
			return domainDTO;
		}).collect(Collectors.toList());
		return new ApiResponse<>(true,"İşlem başarılı.",dtos);
	}
	@Override
	public ApiResponse findById(Long id) {
		Domain domain = domainRepository.findById(id).orElse(null);
		if (domain==null){
			return new ApiResponse<>(false,"Domain bulunamadı.",null);
		}
		DomainDTO domainDTO = this.modelMapperServiceImpl.response().map(domain, DomainDTO.class);
		domainDTO.setId(domain.getId());
		domainDTO.setUrl(domain.getUrl());
		domainDTO.setAd(domain.getAd());
		domainDTO.setRole(domain.getRole().toDTO());
		domainDTO.setAnaDomainMi(domain.isAnaDomainMi());
		domainDTO.setDeleted(domain.getDeleted());

		domainDTO.setSliders(domain.getSliders().stream().map(slider -> this.modelMapperServiceImpl.response().map(slider, SliderDTO.class)).collect(Collectors.toList()));
		domainDTO.setDomainLogos(domain.getDomainLogos().stream().map(domainLogo -> this.modelMapperServiceImpl.response().map(domainLogo, DomainLogoDTO.class)).collect(Collectors.toList()));
		domainDTO.setHaberList(domain.getHaberList().stream().map(haber -> this.modelMapperServiceImpl.response().map(haber, HaberDTO.class)).collect(Collectors.toList()));
		domainDTO.setDuyuruList(domain.getDuyuruList().stream().map(duyuru -> this.modelMapperServiceImpl.response().map(duyuru,DuyuruDTO.class)).collect(Collectors.toList()));
		domainDTO.setMenuList(domain.getMenuList().stream().map(menu -> this.modelMapperServiceImpl.response().map(menu, MenuDTO.class)).collect(Collectors.toList()));

		return new ApiResponse<>(true,"İşlem başarılı.",domainDTO);
	}

	@Override
	public void deleteById(Long id) {
	if (domainRepository.existsById(id)){
		domainRepository.deleteById(id);
	}
	}

}
