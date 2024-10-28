package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnaSayfaSliderRepository extends JpaRepository<AnaSayfaSlider, Long> {
	List<AnaSayfaSlider> findAllByMenuAnaSayfaMiAndMenuNewDomain_Id(boolean anaSayfaMi, Long domainId);
}
