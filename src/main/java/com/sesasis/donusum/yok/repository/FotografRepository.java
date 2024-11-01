package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Fotograf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FotografRepository extends JpaRepository<Fotograf,Long> {
    @Query("SELECT MAX(f.siraNo) FROM Fotograf f")
    Optional<Long> findMaxSiraNo();
}
