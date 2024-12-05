package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AltMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AltMenuRepository extends JpaRepository<AltMenu, Long> {
	List<AltMenu> findAllByAnaMenuDomainId(Long altMenuDTO);

	@Query("SELECT COUNT(a) > 0 FROM AltMenu a WHERE a.url = :url")
	boolean existsByUrl(@Param("url") String url);



}
