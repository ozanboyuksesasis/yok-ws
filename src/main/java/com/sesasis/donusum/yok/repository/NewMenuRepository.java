package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.entity.NewMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewMenuRepository extends JpaRepository<NewMenu, Long> {

    List<Menu> findAllByNewDomain_Id(Long domainId);

    NewMenu findOneByNewDomain_IdAndAnaSayfaMi(Long domainId, boolean anaSayfaMi);

    List<Menu> findAllByNewDomain_IdAndAnaSayfaMi(Long domainId, boolean anaSayfaMi);
}
