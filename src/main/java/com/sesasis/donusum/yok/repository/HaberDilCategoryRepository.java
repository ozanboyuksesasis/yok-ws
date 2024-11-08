package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.HaberDilCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HaberDilCategoryRepository extends JpaRepository<HaberDilCategory, Long> {

    //List<HaberDilCategory> findAllByOrderBySiraNoDesc();
}

