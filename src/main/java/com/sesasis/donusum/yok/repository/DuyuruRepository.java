package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Duyuru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DuyuruRepository extends JpaRepository<Duyuru, Long> {

    List<Duyuru> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX(d.siraNo) FROM Duyuru d")
    Optional<Long> findMaxSiraNo();
}
