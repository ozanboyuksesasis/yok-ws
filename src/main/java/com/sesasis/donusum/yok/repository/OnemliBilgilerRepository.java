package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Haber;
import com.sesasis.donusum.yok.entity.OnemliBilgiler;
import com.sesasis.donusum.yok.service.OnemliBilgilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


public interface OnemliBilgilerRepository extends JpaRepository<OnemliBilgiler,Long> {
    List<OnemliBilgiler> findByDomain_IdAndOnemliBilgilerDilCategory_IdOrderBySiraNoDesc(Long domainId, Long dilCategoryId);
    @Query("SELECT COALESCE(MAX(o.siraNo), 0) FROM OnemliBilgiler o")
    Long findMaxSiraNo();

}
