package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Duyuru;
import com.sesasis.donusum.yok.entity.Haber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DuyuruRepository extends JpaRepository<Duyuru, Long> {

    List<Duyuru> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX(d.siraNo) FROM Duyuru d")
    Optional<Long> findMaxSiraNo();

    List<Duyuru> findAllByOrderBySiraNoDesc();

    List<Duyuru> findByDomain_IdAndGenelDilCategory_IdAndAktifMiTrueOrderBySiraNoDesc(Long domainId, Long dilCategoryId);

    List<Duyuru> findByDomain_IdAndGenelDilCategory_IdAndAktifMiFalseOrderBySiraNoDesc(Long domainId, Long dilCategoryId);
    List<Duyuru> findAllByDomainId(Long domainId);

}



