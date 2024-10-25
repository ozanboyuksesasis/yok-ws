package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.utils.SecurityContextUtil;
import com.sesasis.donusum.yok.dto.AnaSayfaSliderDTO;
import com.sesasis.donusum.yok.dto.OnizleDTO;
import com.sesasis.donusum.yok.entity.AnaSayfaSlider;
import com.sesasis.donusum.yok.entity.AnaSayfaSolContent;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.repository.AnaSayfaSliderRepository;
import com.sesasis.donusum.yok.repository.AnaSayfaSolContentRepository;
import com.sesasis.donusum.yok.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnizleService {

	private final AnaSayfaSliderRepository anaSayfaSliderRepository;
	private final AnaSayfaSolContentRepository anaSayfaSolContentRepository;
	private final SecurityContextUtil securityContextUtil;
	private final FileService fileService;

	public ApiResponse onizle(){
	/*	List<AnaSayfaSlider> anaSayfaSliderList = anaSayfaSliderRepository.findAllByMenuAnaSayfaMiAndMenuDomainId(Boolean.TRUE,securityContextUtil.getCurrentUser().getLoggedDomain().getId());
		List<AnaSayfaSolContent> anaSayfaSolContentList = anaSayfaSolContentRepository.findAllByMenuAnaSayfaMiAndMenuDomainId(Boolean.TRUE,securityContextUtil.getCurrentUser().getLoggedDomain().getId());

		if (anaSayfaSliderList.isEmpty() && anaSayfaSolContentList.isEmpty()){
			return new ApiResponse(false, "Önizlemek için önce kontent girmelisiniz.",null);
		}

		OnizleDTO onizleDTO = new OnizleDTO();

		if (!anaSayfaSliderList.isEmpty()){
			List<AnaSayfaSliderDTO> anaSayfaSliderDTOList = anaSayfaSliderRepository.findAllByMenuAnaSayfaMiAndMenuDomainId(Boolean.TRUE,securityContextUtil.getCurrentUser().getLoggedDomain().getId()).stream().map(e->{
				AnaSayfaSliderDTO anaSayfaSliderDTO = e.toDTO();
				try {
					anaSayfaSliderDTO.setBase64content(fileService.getFileAsBase64(e.getPath()));
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}

				return anaSayfaSliderDTO;
			}).collect(Collectors.toList());
			onizleDTO.setSliderList(anaSayfaSliderDTOList);
		}

		onizleDTO.setSolContentList(!anaSayfaSolContentList.isEmpty() ? anaSayfaSolContentList.stream().map(e->e.toDTO()).collect(Collectors.toList()): new ArrayList<>());

		return new ApiResponse(true, MessageConstant.SUCCESS,onizleDTO);
	}*/
		return null;
}
}
