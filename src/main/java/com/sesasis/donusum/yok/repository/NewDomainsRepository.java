package com.sesasis.donusum.yok.repository;

import com.sesasis.donusum.yok.entity.NewDomain;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewDomainsRepository extends JpaRepository<NewDomain, Long> {

}
