package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AltMenuRepository extends JpaRepository<AltMenu, Long> {
	List<AltMenu> findAllByMenuDomainId(Long altMenuDTO);

	AltMenu findOneByIdAndMenuId_DomainId(Long menuId,Long domainId);

	@Query("SELECT COUNT(a) > 0 FROM AltMenu a WHERE a.url = :url")
	boolean existsByUrl(@Param("url") String url);

	@Query("SELECT MAX(m.groupId) FROM AltMenu m")
	Optional<Long> findMaxGroupId();

	List<AltMenu> findAllByGroupIdAndDomain_Id(Long groupId, Long domainId);

	boolean existsByUrlAndDomain_Id(String url,Long domainId);


}
