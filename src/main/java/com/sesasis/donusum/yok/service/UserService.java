package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.dto.UserDTO;
import com.sesasis.donusum.yok.core.security.models.User;
import com.sesasis.donusum.yok.core.security.repository.UserRepository;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends AbstractService<User, UserRepository> implements IService<UserDTO> {
	private final PasswordEncoder encoder;

	public UserService(UserRepository repository, PasswordEncoder encoder) {
		super(repository);
		this.encoder = encoder;
	}

	@Override
	@Transactional
	public ApiResponse save(UserDTO userDTO) {

		if (getRepository().existsByUsername(userDTO.getUsername())) {
			return new ApiResponse(false, "Kullanıcı adı zaten kullanılıyor.", userDTO.getUsername());
		}

		User user = userDTO.toEntity();
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user.setRoleList(userDTO.getRoleList());
		user.setAktif(Boolean.TRUE);
		getRepository().save(user);

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
