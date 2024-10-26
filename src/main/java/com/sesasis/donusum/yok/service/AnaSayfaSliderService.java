package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.AbstractService;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.core.utils.GeneralUtils;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.AnaSayfaSliderRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnaSayfaSliderService implements IService<AnaSayfaSliderDTO> {

	private final SecurityContextUtil securityContextUtil;
	private final MenuRepository menuRepository;
	private final FileService fileService;
	private final ModelMapperServiceImpl modelMapperServiceImpl;
	private final AnaSayfaSliderRepository anaSayfaSliderRepository;
	public AnaSayfaSliderService(SecurityContextUtil securityContextUtil, MenuRepository menuRepository, FileService fileService, ModelMapperServiceImpl modelMapperServiceImpl, AnaSayfaSliderRepository anaSayfaSliderRepository) {

		this.securityContextUtil = securityContextUtil;
		this.menuRepository = menuRepository;
		this.fileService = fileService;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
        this.anaSayfaSliderRepository = anaSayfaSliderRepository;
    }


	@Override
	@Transactional
	public ApiResponse save(AnaSayfaSliderDTO anaSayfaSliderDTO) {
		/*getRepository().save(anaSayfaSliderDTO.toEntity());
		return new ApiResponse(true, MessageConstant.SAVE_MSG, null);*/
		return null;
	}

	@Override
	public ApiResponse findAll() {
		/*List<AnaSayfaSliderDTO> anaSayfaSliderDTOList = getRepository().findAllByMenuAnaSayfaMiAndMenuDomainId(Boolean.TRUE,securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e->{
			AnaSayfaSliderDTO anaSayfaSliderDTO = e.toDTO();
			try {
				anaSayfaSliderDTO.setBase64content(fileService.getFileAsBase64(e.getPath()));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

			return anaSayfaSliderDTO;
		}).collect(Collectors.toList());

		return new ApiResponse(true,MessageConstant.SUCCESS, anaSayfaSliderDTOList);*/
		return null;
	}

	@Override
	public ApiResponse findById(Long id) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Transactional
	public ApiResponse saveWithFile(AnaSayfaSliderDTO anaSayfaSliderDTO, MultipartFile[] files) {
		/*AnaSayfaSlider anaSayfaSlider = anaSayfaSliderDTO.toEntity();
		String path = null;

		Menu anasayfa = menuRepository.findOneByDomainIdAndAnaSayfaMi(securityContextUtil.getCurrentUser().getLoggedDomain().getId(),Boolean.TRUE);

		anaSayfaSlider.setMenu(anasayfa);

		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				String generatedName = GeneralUtils.generateFileName(file);
				try {
					path = fileService.saveFile(file, generatedName).toFile().getAbsolutePath();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		if (GeneralUtils.valueNullOrEmpty(anaSayfaSlider.getId())) {
			anaSayfaSlider.setPath(path);
			getRepository().save(anaSayfaSlider);
			return new ApiResponse(true, MessageConstant.SAVE_MSG, null);
		} else {
			if (!GeneralUtils.valueNullOrEmpty(path) && !path.isEmpty()) {
				anaSayfaSlider.setPath(path);
			}
			getRepository().save(anaSayfaSlider);
			return new ApiResponse(true, MessageConstant.UPDATE_MSG, null);
		}*/
		return null;
	}
}
