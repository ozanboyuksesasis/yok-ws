package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByDomainId(Long domainId);
	Menu findOneByIdAndDomain_Id(Long menuId,Long domainId);
	Menu findOneByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	List<Menu> findAllByChildIdAndDomain_Id(Long groupId, Long domainId);
	List<Menu> findAllByDomainIdAndAnaSayfaMi(Long domainId,boolean anaSayfaMi);
	boolean existsByUrlAndDomain_Id(String url, Long domainId);

	@Query("SELECT MAX(d.groupId) FROM Menu d")
	Optional<Long> findMaxGroupId();
	@Query("SELECT MAX(d.parentId) FROM Menu d")
	Optional<Long> findMaxParentId();
	List<Menu> findAllByParentIdAndDomain_Id(Long parentId,Long domainId);
}
