package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.models.User;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.AnaBaslikDTO;
import com.sesasis.donusum.yok.dto.DomainDTO;
import com.sesasis.donusum.yok.entity.AnaBaslik;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AnaBaslikRepository;
import com.sesasis.donusum.yok.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService extends AbstractService<Domain, DomainRepository> implements IService<DomainDTO> {

	private  final RoleRepository roleRepository;
	private final DomainRepository domainRepository;
	private final AnaBaslikRepository anaBaslikRepository;
	private final ModelMapperServiceImpl modelMapperService;

	public DomainService(DomainRepository repository, RoleRepository roleRepository, DomainRepository domainRepository, AnaBaslikRepository anaBaslikRepository, ModelMapperServiceImpl modelMapperService) {
		super(repository);
		this.roleRepository = roleRepository;
		this.domainRepository = domainRepository;
        this.anaBaslikRepository = anaBaslikRepository;
        this.modelMapperService = modelMapperService;
    }


	@Override
	@Transactional
	public ApiResponse save(DomainDTO domainDTO) {

		if (domainDTO.getId() == null) {
			if (getRepository().existsByAd(domainDTO.getAd())) {
				return new ApiResponse(false, "Domain adı zaten kullanılıyor.", domainDTO.getAd());
			}else{

				Domain existsAnaDomain = domainRepository.findOneByAnaDomainMi(Boolean.TRUE);

				if (existsAnaDomain != null && domainDTO.isAnaDomainMi()) {
					return new ApiResponse(false, "Birden fazla ana domain kaydedemezsiniz.", null);
				}

				Domain domain = domainDTO.toEntity();
				getRepository().save(domain);


				if (domainDTO.isAnaDomainMi()){//ana domain menüleri farklı olacak
					DashboardMenu yok = new DashboardMenu();
					yok.setId(11L);
					DashboardMenu anasayfaSlider = new DashboardMenu();
					anasayfaSlider.setId(12L);
					DashboardMenu anasayfaDuyuru = new DashboardMenu();
					anasayfaDuyuru.setId(13L);
					DashboardMenu anasayfaHaber = new DashboardMenu();
					anasayfaHaber.setId(14L);
					DashboardMenu anasayfaHizliBaglantilarVeStory = new DashboardMenu();
					anasayfaHizliBaglantilarVeStory.setId(15L);

					List<DashboardMenu> dashboardMenuList = new ArrayList<>();
					dashboardMenuList.add(yok);
					dashboardMenuList.add(anasayfaSlider);
					dashboardMenuList.add(anasayfaHaber);
					dashboardMenuList.add(anasayfaDuyuru);

					Role role = roleRepository.findById(domainDTO.getRole().getId()).get();
					role.setDashboardMenuList(dashboardMenuList);
					roleRepository.save(role);
				}else{
					DashboardMenu tanimlamalar = new DashboardMenu();
					tanimlamalar.setId(1L);
					DashboardMenu menuOlustur = new DashboardMenu();
					menuOlustur.setId(6L);
					DashboardMenu altMenuOlustur = new DashboardMenu();
					altMenuOlustur.setId(7L);
					DashboardMenu anaSayfaMenuIcerik = new DashboardMenu();
					anaSayfaMenuIcerik.setId(8L);
					DashboardMenu menuIcerikDoldur = new DashboardMenu();
        			  menuIcerikDoldur.setId(9L);
					DashboardMenu IkinciSutunAltMenuEkle = new DashboardMenu();
					IkinciSutunAltMenuEkle.setId(10L);

					List<DashboardMenu> dashboardMenuList = new ArrayList<>();
					dashboardMenuList.add(tanimlamalar);
					dashboardMenuList.add(menuOlustur);
					dashboardMenuList.add(altMenuOlustur);
					dashboardMenuList.add(anaSayfaMenuIcerik);
					dashboardMenuList.add(menuIcerikDoldur);
					dashboardMenuList.add(IkinciSutunAltMenuEkle);
					Role role = roleRepository.findById(domainDTO.getRole().getId()).get();
					role.setDashboardMenuList(dashboardMenuList);
					roleRepository.save(role);
				}
			}
		}else{
			Domain existsAnaDomain = domainRepository.findOneByAnaDomainMiAndIdNot(Boolean.TRUE, domainDTO.getId());
			if (existsAnaDomain != null && domainDTO.isAnaDomainMi()) {
				return new ApiResponse(false, "Birden fazla ana domain kaydedemezsiniz.", null);
			}
			Domain existsDomain = domainRepository.findById(domainDTO.getId()).get();
			existsDomain.setAd(domainDTO.getAd());
			existsDomain.setUrl(domainDTO.getUrl());
			existsDomain.setAnaDomainMi(domainDTO.isAnaDomainMi());
			existsDomain.setRole(domainDTO.getRole().toEntity());
			getRepository().save(existsDomain);
		}
		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}
	@Override
	public ApiResponse findAll() {
		return new ApiResponse(true, MessageConstant.SUCCESS, domainRepository.findAllByOrderByAnaDomainMiDesc());
	}

	@Override
	public ApiResponse findById(Long id) {
		Domain domain = domainRepository.findById(id).orElse(null);
		if (domain == null) {
			return new ApiResponse<>(false, "Domain bulunamadı.", null);
		}
		//AnaBaslik anaBaslik = domain.getAnaBaslik();
		//domain.setAnaBaslik(anaBaslik);

		DomainDTO domainDTO = modelMapperService.response().map(domain, DomainDTO.class);

		return new ApiResponse<>(true, "İşlem başarılı.", domainDTO);
	}

	@Override
	public void deleteById(Long id) {

	}

}