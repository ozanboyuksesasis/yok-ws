package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.Haber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HaberRepository extends JpaRepository<Haber, Long> {

    List<Haber> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(MAX(h.siraNo), 0) FROM Haber h")
    Optional<Long> findMaxSiraNo();

    List<Haber> findAllByOrderBySiraNoDesc();

    List<Haber> findAllByDomainId(Long domainId);

List<Haber> findByDomain_IdAndGenelDilCategory_IdAndAktifMiTrueOrderBySiraNoDesc(Long domainId , Long dilCategoryId);
List<Haber> findByDomain_IdAndGenelDilCategory_IdAndAktifMiFalseOrderBySiraNoDesc(Long domainId, Long dilCategoryId);
}
