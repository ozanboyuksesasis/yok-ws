package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Dosya;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DosyaRepository extends JpaRepository<Dosya,Long> {

    List<Dosya> findAllByDomain_Id(Long domainId);
}
