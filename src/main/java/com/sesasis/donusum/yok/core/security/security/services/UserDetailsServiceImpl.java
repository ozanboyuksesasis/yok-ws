package com.sesasis.donusum.yok.core.security.security.services;

import com.sesasis.donusum.yok.core.security.models.User;
import com.sesasis.donusum.yok.core.security.repository.UserRepository;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final DomainRepository domainRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    Set<Domain> domainList = new HashSet<>();

    user.getRoleList().forEach(role->{
      domainList.addAll(domainRepository.findByRoleId(role.getId()));
    });

    return UserDetailsImpl.build(user,domainList);
  }

}
