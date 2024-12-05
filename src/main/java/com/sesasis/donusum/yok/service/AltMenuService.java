package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AltMenuDTO;
import com.sesasis.donusum.yok.entity.AltMenu;
import com.sesasis.donusum.yok.entity.Domain;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AltMenuRepository;
import com.sesasis.donusum.yok.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AltMenuService extends AbstractService<AltMenu, AltMenuRepository> implements IService<AltMenuDTO> {

    private final SecurityContextUtil securityContextUtil;
    private final AltMenuRepository altMenuRepository;
    private final DomainRepository domainRepository;
    private final ModelMapperServiceImpl modelMapperService;

    public AltMenuService(AltMenuRepository repository, SecurityContextUtil securityContextUtil,
                          AltMenuRepository altMenuRepository, DomainRepository domainRepository, ModelMapperServiceImpl modelMapperService) {
        super(repository);
        this.securityContextUtil = securityContextUtil;
        this.altMenuRepository = altMenuRepository;
        this.domainRepository = domainRepository;
        this.modelMapperService = modelMapperService;
    }


    @Override
    @Transactional
	public ApiResponse save(AltMenuDTO altMenuDTO) {
		AltMenu altMenu = modelMapperService.request().map(altMenuDTO, AltMenu.class);
		altMenu.setUrl(altMenu.getUrl() + "-" + UUID.randomUUID().toString().substring(0, 8));
		altMenuRepository.save(altMenu);

		return new ApiResponse(true, "Alt Menü başarıyla kaydedildi: " , null);
	}

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true, MessageConstant.SUCCESS, getRepository().findAllByAnaMenuDomainId(securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e -> e.toDTO()).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
