package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AnaBaslik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnaBaslikRepository extends JpaRepository<AnaBaslik, Long> {
    AnaBaslik findOneByDomainId(Long domainId);
    boolean existsById(Long domainId);
}
