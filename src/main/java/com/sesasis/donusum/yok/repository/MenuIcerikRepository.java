package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.MenuIcerik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuIcerikRepository extends JpaRepository<MenuIcerik, Long> {
	List<MenuIcerik> findAllByAltMenuAnaMenuDomainId(Long domainId);
	MenuIcerik findOneByAltMenuAnaMenuDomainIdAndAltMenuUrl(Long domainId,String altMenuUrl);
}
