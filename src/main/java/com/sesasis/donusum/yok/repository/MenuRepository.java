package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByDomainId(Long domainId);
	Menu findOneByIdAndDomainId(Long menuId,Long domainId);
	Menu findOneByIdAndDomain_Id(Long menuId,Long domainId);
	Menu findOneByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	List<Menu> findAllByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	Boolean  existsByUrl(String url);

}
