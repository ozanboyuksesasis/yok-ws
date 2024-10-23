package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.DashboardMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashBoardMenuRepository extends JpaRepository<DashboardMenu,Long> {
    boolean existsByName(String name);
}
