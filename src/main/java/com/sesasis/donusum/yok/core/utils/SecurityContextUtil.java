package com.sesasis.donusum.yok.core.utils;

import com.sesasis.donusum.yok.core.domain.UserInfoDTO;
import com.sesasis.donusum.yok.core.security.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SecurityContextUtil {
	public UserInfoDTO getCurrentUser() {

		try {
			if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				Object principal = authentication.getPrincipal();
				UserInfoDTO userInfoDTO = new UserInfoDTO();

				if (principal instanceof String) {
					userInfoDTO.setUserName((String) principal);
					userInfoDTO.setRoles(new ArrayList<>());
					userInfoDTO.setUserId(null);
				} else {
					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					List<String> roles = userDetails.getAuthorities().stream()
							.map(item -> item.getAuthority())
							.collect(Collectors.toList());

					userInfoDTO.setUserId(userDetails.getId());
					userInfoDTO.setUserName(userDetails.getUsername());
					userInfoDTO.setRoles(roles);
					userInfoDTO.setLoggedDomain(userDetails.getLoggedDomain());
				}

				return userInfoDTO;
			}

		} catch (Exception ex) {
			System.out.println("Ex :" + ex.getLocalizedMessage());
		}

		return null;

	}
}
