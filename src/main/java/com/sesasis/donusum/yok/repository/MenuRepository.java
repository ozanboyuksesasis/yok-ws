package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByDomainId(Long domainId);
	Menu findOneByIdAndDomainId(Long menuId,Long domainId);
	Menu findOneByIdAndDomain_Id(Long menuId,Long domainId);
	Menu findOneByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	List<Menu> findAllByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	Boolean  existsByUrl(String url);

	@Query("SELECT MAX(d.groupId) FROM Menu d")
	Optional<Long> findMaxGroupId();
}
