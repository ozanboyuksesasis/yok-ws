package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain,Long> {
	boolean existsByAd(String name);
	List<Domain> findByRoleId(Long roleId);
}
