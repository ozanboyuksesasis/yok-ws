package com.sesasis.donusum.yok.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GaleriRepository extends JpaRepository<Galeri,Long> {
    List<Galeri> findAllByDomain_Id(Long domainId);
}
