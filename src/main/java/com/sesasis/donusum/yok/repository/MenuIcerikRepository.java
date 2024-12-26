package com.sesasis.donusum.yok.repository;
import com.sesasis.donusum.yok.entity.MenuIcerik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MenuIcerikRepository extends JpaRepository<MenuIcerik, Long> {
	//List<MenuIcerik> findAllByAltMenuAnaMenuDomainId(Long domainId);
	//MenuIcerik findOneByAltMenuAnaMenuDomainIdAndAltMenuUrl(Long domainId,String altMenuUrl);
    List<MenuIcerik> findAllByMenuDomainId(Long domainId);
    MenuIcerik findOneByMenuId(Long menuId);
    List<MenuIcerik> findAllByDomainId(Long domainId);
    List<MenuIcerik> findAllByGroupIdAndDomain_Id(Long groupId, Long domainId);
    @Query("SELECT MAX(m.groupId) FROM MenuIcerik m")
    Optional<Long> findMaxGroupId();
}
