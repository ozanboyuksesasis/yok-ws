package com.sesasis.donusum.yok.core.security.repository;

import com.sesasis.donusum.yok.core.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  boolean existsByAd(String name);

}
