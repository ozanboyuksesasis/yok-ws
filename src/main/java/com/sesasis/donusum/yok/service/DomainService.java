package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DomainDTO;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomainService  implements IService<DomainDTO> {

	private  final RoleRepository roleRepository;
	private  final DomainRepository domainRepository;
	private final ModelMapperServiceImpl modelMapperServiceImpl;

	public DomainService(DomainRepository repository, RoleRepository roleRepository, DomainRepository domainRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
		this.roleRepository = roleRepository;
        this.domainRepository = domainRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }


	@Override
	@Transactional
	public ApiResponse save(DomainDTO domainDTO) {
		if (domainRepository.existsByAd(domainDTO.getAd())) {
			return new ApiResponse(false, "Domain adı zaten kullanılıyor.", domainDTO.getAd());
		}

		Domain domain = modelMapperServiceImpl.request().map(domainDTO, Domain.class);
		domainRepository.save(domain);

		if (!domainDTO.isAnaDomainMi()) { // Eğer ana domain değilse, farklı menüler atanacak
			List<DashboardMenu> dashboardMenuList = getDefaultDashboardMenus();

			Optional<Role> optionalRole = roleRepository.findById(domainDTO.getRoleId());
			if (optionalRole.isPresent()) {
				Role role = optionalRole.get();
				role.setDashboardMenuList(dashboardMenuList);
				roleRepository.save(role);
			} else {
				return new ApiResponse(false, "Rol bulunamadı.", null);
			}
		}

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	private List<DashboardMenu> getDefaultDashboardMenus() {
		List<DashboardMenu> dashboardMenuList = new ArrayList<>();

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

		dashboardMenuList.add(tanimlamalar);
		dashboardMenuList.add(menuOlustur);
		dashboardMenuList.add(altMenuOlustur);
		dashboardMenuList.add(anaSayfaMenuIcerik);
		dashboardMenuList.add(altMenuIcerik);

		return dashboardMenuList;
	}

	@Override
	public ApiResponse findAll() {
		return null;
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

}
