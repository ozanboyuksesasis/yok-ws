package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personel,Long> {
    boolean existsByIsimAndSoyisimAndEmail(String isim, String soyisim, String email);

}
