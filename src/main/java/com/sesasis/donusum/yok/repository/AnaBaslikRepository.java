package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AnaBaslik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnaBaslikRepository extends JpaRepository<AnaBaslik, Long> {
    AnaBaslik findOneByDomainId(Long domainId);
}
