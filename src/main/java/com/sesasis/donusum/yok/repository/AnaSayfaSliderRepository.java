package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnaSayfaSliderRepository extends JpaRepository<AnaSayfaSlider, Long> {
	//List<AnaSayfaSlider> findAllByMenuAnaSayfaMiAndMenuDomainId(boolean anaSayfami, Long domainId);

	List<AnaSayfaSlider> findAllByOrderBySiraNoAsc();
	List<AnaSayfaSlider> findAllByGenelDilCategoryIdOrderBySiraNoAsc(Long genelDilCategoryId);
	List<AnaSayfaSlider> findAllBySiraNo(Long siraNo);
	@Query(value = "SELECT * FROM public.ana_sayfa_slider WHERE sira_no IN (" +
			"SELECT sira_no FROM public.ana_sayfa_slider WHERE id = :id)",
			nativeQuery = true)
	List<AnaSayfaSlider> findSlidersById(@Param("id") Long id);
}
