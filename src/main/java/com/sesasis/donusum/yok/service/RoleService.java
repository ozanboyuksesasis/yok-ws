package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.RoleDTO;
import com.sesasis.donusum.yok.core.security.models.Role;
import com.sesasis.donusum.yok.core.security.repository.RoleRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService  implements IService<RoleDTO> {
		private final RoleRepository roleRepository;
		private final ModelMapperServiceImpl modelMapperServiceImpl;



	@Override
	@Transactional
	public ApiResponse save(RoleDTO roleDTO) {
		Boolean response = existsByAd(roleDTO.getAd());
		if (response) {
			return new ApiResponse<>(false,"Bu rol daha önce kayıt edilmiştir.",null);
		}
		Role role =this.modelMapperServiceImpl.request().map(roleDTO, Role.class);
		Role save = roleRepository.save(role);
		RoleDTO dto = this.modelMapperServiceImpl.response().map(save, RoleDTO.class);

		return new ApiResponse(true, MessageConstant.SAVE_MSG, dto);
	}

	@Override
	public ApiResponse findAll() {
		List<Role> roles = roleRepository.findAll();
		if (roles.isEmpty()) {
			return new ApiResponse<>(false,"Liste boş.",null);
		}
		List<RoleDTO> dtos = roles.stream().map(role ->
				this.modelMapperServiceImpl.response().map(role, RoleDTO.class)).collect(Collectors.toList());

		return new ApiResponse<>(true,"İşlem başarılı.",dtos);
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	public boolean existsByAd(String ad) {
		return roleRepository.existsByAd(ad);
	}

}
