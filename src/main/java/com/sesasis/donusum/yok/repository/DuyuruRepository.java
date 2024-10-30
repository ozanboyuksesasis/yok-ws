package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Duyuru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DuyuruRepository extends JpaRepository<Duyuru, Long> {

    List<Duyuru> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX(h.siraNo) FROM Haber h")
    Optional<Long> findMaxSiraNo();

}
