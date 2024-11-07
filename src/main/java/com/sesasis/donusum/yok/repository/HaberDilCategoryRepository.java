package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.HaberDilCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaberDilCategoryRepository extends JpaRepository<HaberDilCategory, Long> {
    HaberDilCategory findByNameContainingIgnoreCase(String name);
}
