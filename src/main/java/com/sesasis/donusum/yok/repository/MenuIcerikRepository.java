package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.MenuIcerik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuIcerikRepository extends JpaRepository<MenuIcerik, Long> {

	// `domainId` alanı yerine `domain_Id` yapısını kullanıyoruz
	List<MenuIcerik> findAllByAltMenuAnaMenuNewDomainId(Long newDomainId);
	MenuIcerik findOneByAltMenuAnaMenuNewDomainIdAndAltMenuUrl(Long newDomainId, String altMenuUrl);

}
