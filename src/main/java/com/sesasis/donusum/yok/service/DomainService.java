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
import com.sesasis.donusum.yok.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService extends AbstractService<Domain, DomainRepository> implements IService<DomainDTO> {

	private  final RoleRepository roleRepository;

	public DomainService(DomainRepository repository, RoleRepository roleRepository) {
		super(repository);
		this.roleRepository = roleRepository;
	}


	@Override
	@Transactional
	public ApiResponse save(DomainDTO domainDTO) {

		if (getRepository().existsByAd(domainDTO.getAd())) {
			return new ApiResponse(false, "Domain adı zaten kullanılıyor.", domainDTO.getAd());
		}

		getRepository().save(domainDTO.toEntity());

		if (domainDTO.isAnaDomainMi()){//ana domain menüleri farklı olacak

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
		}

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
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
