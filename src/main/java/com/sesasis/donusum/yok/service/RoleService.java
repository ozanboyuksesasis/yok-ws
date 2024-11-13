package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.DomainDTO;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends AbstractService<Role, RoleRepository> implements IService<RoleDTO> {

	private final RoleRepository roleRepository;
	private final ModelMapperServiceImpl modelMapperService;

	public RoleService(RoleRepository repository,
                       RoleRepository roleRepository, ModelMapperServiceImpl modelMapperService) {
		super(repository);
		this.roleRepository = roleRepository;
        this.modelMapperService = modelMapperService;
    }

	@Override
	@Transactional
	public ApiResponse save(RoleDTO roleDTO) {

		if (getRepository().existsByAd(roleDTO.getAd())) {
			return new ApiResponse(false, "Rol adı zaten kullanılıyor.", roleDTO.getAd());
		}

		getRepository().save(roleDTO.toEntity());

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		List<Role> roles = roleRepository.findAll();
		if (roles.isEmpty()){
			return new ApiResponse<>(false,"Rol listesi boş.",null);
		}
		List<RoleDTO> dtos = roles.stream().map(role -> this.modelMapperService.response().map(role, RoleDTO.class)).collect(Collectors.toList());

		return new ApiResponse<>(true,"İşlem başarılı.",dtos);
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
	if (roleRepository.existsById(id)){
		roleRepository.deleteById(id);
	}
	}

	public boolean existsByAd(String ad) {
		return getRepository().existsByAd(ad);
	}

}
