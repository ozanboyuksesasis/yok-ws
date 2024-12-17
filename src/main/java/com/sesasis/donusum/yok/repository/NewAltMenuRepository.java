package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.NewAltMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewAltMenuRepository extends JpaRepository<NewAltMenu,Long> {

    @Query("SELECT MAX(m.groupId) FROM NewAltMenu m")
    Optional<Long> findMaxGroupId();

}
