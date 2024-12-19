package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.NewAltMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewAltMenuRepository extends JpaRepository<NewAltMenu,Long> {

    @Query("SELECT MAX(m.groupId) FROM NewAltMenu m")
    Optional<Long> findMaxGroupId();

    List<NewAltMenu> findAllByDomain_Id(Long domainId);

    NewAltMenu findOneByIdAndDomainId(Long newAltmenuId,Long domainId);
    List<NewAltMenu> findAllByGroupIdAndDomain_Id(Long groupId , Long domainId);

}
