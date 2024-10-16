package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.dto.PersonalDTO;
import com.sesasis.donusum.yok.entity.GorevDonemi;
import com.sesasis.donusum.yok.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GorevDonemiRepository extends JpaRepository<GorevDonemi,Long> {
    @Query("SELECT gd FROM GorevDonemi gd WHERE gd.personel.id = :personelId AND gd.cikisTarihi IS NULL")
    GorevDonemi findByPersonelIdAndCikisTarihiIsNull(@Param("personelId") Long personelId);


    Optional<GorevDonemi> findByPersonelAndCikisTarihiIsNull(Personel personel);

}
