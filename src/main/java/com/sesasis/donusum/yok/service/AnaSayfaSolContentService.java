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
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
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
public class AnaSayfaSolContentService implements IService<AnaSayfaSolContentDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final MenuRepository menuRepository;
	private final FileService fileService;
	private final ModelMapperServiceImpl modelMapperServiceImpl;
	private final AnaSayfaSolContentRepository anaSayfaSolContentRepository;

    public AnaSayfaSolContentService(SecurityContextUtil securityContextUtil, MenuRepository menuRepository, FileService fileService, ModelMapperServiceImpl modelMapperServiceImpl, AnaSayfaSolContentRepository anaSayfaSolContentRepository) {
        this.securityContextUtil = securityContextUtil;
        this.menuRepository = menuRepository;
        this.fileService = fileService;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
        this.anaSayfaSolContentRepository = anaSayfaSolContentRepository;
    }


    @Override
	@Transactional
	public ApiResponse save(AnaSayfaSolContentDTO anaSayfaSolContentDTO) {
		AnaSayfaSolContent anaSayfaSolContent = modelMapperServiceImpl.request().map(anaSayfaSolContentDTO, AnaSayfaSolContent.class);

		// Mevcut Domain'de ana sayfa menüsünü bul ve ata
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		Menu anasayfa = menuRepository.findOneByNewDomain_IdAndAnaSayfaMi(domainId, Boolean.TRUE);
		anaSayfaSolContent.setMenu(anasayfa);

		// Veritabanına kaydet
		anaSayfaSolContentRepository.save(anaSayfaSolContent);

		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
	}

	@Override
	public ApiResponse findAll() {
		Long domainId = securityContextUtil.getCurrentUser().getLoggedDomain().getId();
		List<AnaSayfaSolContentDTO> anaSayfaSolContentDTOList = anaSayfaSolContentRepository
				.findAllByMenuAnaSayfaMiAndMenuNewDomain_Id(Boolean.TRUE, domainId)
				.stream()
				.map(content -> modelMapperServiceImpl.response().map(content, AnaSayfaSolContentDTO.class))
				.collect(Collectors.toList());

		return new ApiResponse(true, MessageConstant.SUCCESS, anaSayfaSolContentDTOList);
	}


	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
