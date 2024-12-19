package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {
    List<Slider> findAllByOrderBySiraNoDesc();

    @Query("SELECT COALESCE(MAX(s.siraNo), 0) FROM Slider s")
    Optional<Long> findMaxSiraNo();
}
