package com.sesasis.donusum.yok.core.controller;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.enums.RoleNames;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.security.payload.request.LoginRequest;
import com.sesasis.donusum.yok.core.security.payload.response.JwtResponse;
import com.sesasis.donusum.yok.core.security.security.jwt.JwtUtils;
import com.sesasis.donusum.yok.core.security.security.services.UserDetailsImpl;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final DomainRepository domainRepository;
	private final JwtUtils jwtUtils;

	@PostMapping(value = "/signin-one",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUserOne(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		boolean hasAdmin=false;
		Optional<String> adminOpt = roles.stream().filter(e-> e.equals(RoleNames.ROLE_ADMIN.toString())).findAny();

		if (adminOpt.isPresent()){
			hasAdmin = true;
		}
		String jwt;

		if (hasAdmin){
			jwt = jwtUtils.generateJwtToken(authentication);
			return ResponseEntity.ok(new ApiResponse<>(true, MessageConstant.SUCCESS, new JwtResponse(jwt,
					userDetails.getId(),
					userDetails.getUsername(),
					roles,
					userDetails.getDomainList(),
					userDetails.getLoggedDomain(),
					userDetails.getDashboardMenuList()
			)));
		}else{
			if (userDetails.getDomainList().size()==1){
				jwt = jwtUtils.generateJwtToken(authentication);
				return ResponseEntity.ok(new ApiResponse<>(true, MessageConstant.SUCCESS, new JwtResponse(jwt,
						userDetails.getId(),
						userDetails.getUsername(),
						roles,
						userDetails.getDomainList(),
						userDetails.getLoggedDomain(),
						userDetails.getDashboardMenuList()
				)));
			}else{
				return ResponseEntity.ok(new ApiResponse<>(true, MessageConstant.SUCCESS, new JwtResponse(userDetails.getDomainList())));
			}
		}
	}

	@PostMapping(value = "/signin-two",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUserTwo(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Domain loggedDomain = null;
		if (loginRequest.getLoggedDomain() != null && loginRequest.getLoggedDomain().getId() != null) {
			loggedDomain = domainRepository.findById(loginRequest.getLoggedDomain().getId())
					.orElseThrow(() -> new IllegalArgumentException("Domain not found."));
		}
		userDetails.setLoggedDomain(loggedDomain);

		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userDetails,
				authentication.getCredentials(),
				authentication.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(newAuth);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new ApiResponse<>(true, MessageConstant.SUCCESS, new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				roles,
				userDetails.getDomainList(),
				loggedDomain,
				userDetails.getDashboardMenuList()
		)));
	}

}
