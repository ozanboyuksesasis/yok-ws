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
	private final UserRepository userRepository;

	public UserService(UserRepository repository, PasswordEncoder encoder, UserRepository userRepository) {
		super(repository);
		this.encoder = encoder;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public ApiResponse save(UserDTO userDTO) {

		if (userDTO.getId() == null) {
			if (getRepository().existsByUsername(userDTO.getUsername())) {
				return new ApiResponse(false, "Kullanıcı adı zaten kullanılıyor.", userDTO.getUsername());
			}else{
				User user = userDTO.toEntity();
				user.setPassword(encoder.encode(userDTO.getPassword()));
				getRepository().save(user);
			}
		}else{
			User existsUser = userRepository.findById(userDTO.getId()).get();

			existsUser.setAd(userDTO.getAd());
			existsUser.setSoyad(userDTO.getSoyad());
			existsUser.setUsername(userDTO.getUsername());
			existsUser.setEmail(userDTO.getEmail());
			existsUser.setAktif(userDTO.isAktif());
			existsUser.setRoleList(userDTO.getRoleList());

			getRepository().save(existsUser);

		}

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		return new ApiResponse(true, MessageConstant.SUCCESS, userRepository.findAll());

	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

}


