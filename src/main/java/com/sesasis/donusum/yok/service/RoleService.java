package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends AbstractService<Role, RoleRepository> implements IService<RoleDTO> {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository repository, RoleRepository roleRepository) {
		super(repository);
		this.roleRepository = roleRepository;
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
		return new ApiResponse(true, MessageConstant.SUCCESS, roleRepository.findAll());
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	public boolean existsByAd(String ad) {
		return getRepository().existsByAd(ad);
	}

}
