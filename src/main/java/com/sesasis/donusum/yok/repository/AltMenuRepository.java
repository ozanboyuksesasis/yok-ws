package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AltMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AltMenuRepository extends JpaRepository<AltMenu, Long> {
	List<AltMenu> findAllByAnaMenuDomainId(Long altMenuDTO);
}
