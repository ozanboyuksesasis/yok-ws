package com.sesasis.donusum.yok.core.security.payload.response;

import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Domain;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> roles;
	private Set<Domain> domainList;
	private Set<DashboardMenu> dashboardMenuList;
	private Domain loggedDomain;

	public JwtResponse(String accessToken, Long id, String username, List<String> roleNameList, Set<Domain> domainList,Domain loggedDomain,Set<DashboardMenu> dashboardMenuList) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roleNameList;
		this.domainList = domainList;
		this.loggedDomain = loggedDomain;
		this.dashboardMenuList = dashboardMenuList;
	}

	public JwtResponse(Set<Domain> domainList) {
		this.domainList = domainList;
	}

}
