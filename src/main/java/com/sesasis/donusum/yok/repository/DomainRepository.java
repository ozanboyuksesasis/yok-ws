package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain,Long> {
	boolean existsByAd(String name);
	List<Domain> findByRoleId(Long roleId);
	Domain findOneByAnaDomainMi(boolean anaDomainMi);
	Domain findOneByAnaDomainMiAndIdNot(boolean anaDomainMi, Long id);
	List<Domain> findAllByOrderByAnaDomainMiDesc();

	@Query("SELECT d FROM Domain d JOIN FETCH d.menus WHERE d.id = :domainId")
	Domain findDomainWithMenus(@Param("domainId") Long domainId);

	@Query("SELECT mi FROM MenuIcerik mi WHERE mi.menu IN :menus")
	List<MenuIcerik> findAllByMenus(@Param("menus") List<Menu> menus);

}
