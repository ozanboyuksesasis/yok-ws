package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AnaSayfaSolContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnaSayfaSolContentRepository extends JpaRepository<AnaSayfaSolContent, Long> {
	List<AnaSayfaSolContent> findAllByMenuAnaSayfaMiAndMenuDomainId(boolean anaSayfami, Long domainId);
}
