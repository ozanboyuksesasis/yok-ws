package com.sesasis.donusum.yok.core.security.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.security.models.User;
import com.sesasis.donusum.yok.entity.DashboardMenu;
import com.sesasis.donusum.yok.entity.Domain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private Set<Domain> domainList;

	private Set<DashboardMenu> dashboardMenuList;

	private Domain loggedDomain;

	public UserDetailsImpl(Long id, String username, String password,
						   Collection<? extends GrantedAuthority> authorities,Set<Domain> domainList,Set<DashboardMenu> dashboardMenuList,Domain loggedDomain) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.domainList = domainList;
		this.dashboardMenuList = dashboardMenuList;
		this.loggedDomain = loggedDomain;
	}

	public static UserDetailsImpl build(User user, Set<Domain> domainList) {
		List<GrantedAuthority> authorities = user.getRoleList().stream()
				.map(role -> new SimpleGrantedAuthority(role.getAd()))
				.collect(Collectors.toList());

		Set<DashboardMenu> dashboardMenus = new HashSet<>();

		user.getRoleList().stream().forEach(role->{
			dashboardMenus.addAll(role.getDashboardMenuList());
		});
		Domain currentDomain = null;
		if (domainList.size() == 1) {
			currentDomain = domainList.stream().findAny().get();
		}
//		else if (domainList.size() > 1) {
//			if (loggedDomain == null) {
//				throw new IllegalArgumentException("Logged domain is not set");
//			}
//			currentDomain = domainList.stream()
//					.filter(domain -> domain.equals(loggedDomain))
//					.findFirst()
//					.orElseThrow(() -> new IllegalArgumentException("Invalid Domain Selection"));
//		}


		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				authorities,
				domainList,
				dashboardMenus,
				currentDomain
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Set<Domain> getDomainList() {
		return domainList;
	}

	public Set<DashboardMenu> getDashboardMenuList() {
		return dashboardMenuList;
	}

	public Domain getLoggedDomain() {
		return loggedDomain;
	}

	public void setLoggedDomain(Domain loggedDomain) {
		this.loggedDomain = loggedDomain;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

}
