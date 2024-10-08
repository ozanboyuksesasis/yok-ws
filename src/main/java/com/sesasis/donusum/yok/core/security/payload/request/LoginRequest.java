package com.sesasis.donusum.yok.core.security.payload.request;

import com.sesasis.donusum.yok.entity.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank
  	private String username;

	@NotBlank
	private String password;

	private Domain loggedDomain;

}
