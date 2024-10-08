package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.GeneralUtils;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.dto.AnaSayfaSolContentDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import com.sesasis.donusum.yok.entity.AnaSayfaSolContent;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.repository.AnaSayfaSliderRepository;
import com.sesasis.donusum.yok.repository.AnaSayfaSolContentRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnaSayfaSolContentService extends AbstractService<AnaSayfaSolContent, AnaSayfaSolContentRepository> implements IService<AnaSayfaSolContentDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final MenuRepository menuRepository;
	private final FileService fileService;

	public AnaSayfaSolContentService(AnaSayfaSolContentRepository repository, SecurityContextUtil securityContextUtil, MenuRepository menuRepository, FileService fileService) {
		super(repository);
		this.securityContextUtil = securityContextUtil;
		this.menuRepository = menuRepository;
		this.fileService = fileService;
	}


	@Override
	@Transactional
	public ApiResponse save(AnaSayfaSolContentDTO anaSayfaSolContentDTO) {
		AnaSayfaSolContent anaSayfaSolContent = anaSayfaSolContentDTO.toEntity();
		Menu anasayfa = menuRepository.findOneByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),Boolean.TRUE);
		anaSayfaSolContent.setMenu(anasayfa);
		getRepository().save(anaSayfaSolContent);

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		return new ApiResponse(true,MessageConstant.SUCCESS, getRepository().findAllByMenuAnaSayfaMiAndMenuDomainId(Boolean.TRUE,securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e->e.toDTO()).collect(Collectors.toList()));
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
