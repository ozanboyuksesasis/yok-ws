package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findAllByNewDomain_Id(Long domainId);

	Menu findOneByNewDomain_IdAndAnaSayfaMi(Long domainId, boolean anaSayfaMi);

	List<Menu> findAllByNewDomain_IdAndAnaSayfaMi(Long domainId, boolean anaSayfaMi);
}
