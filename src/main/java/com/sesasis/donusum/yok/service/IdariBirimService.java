package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.IdariBirimDTO;
import com.sesasis.donusum.yok.entity.IdariBirim;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.IdariBirimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class IdariBirimService implements IService<IdariBirimDTO> {

    private ModelMapperServiceImpl modelMapperServiceImpl;
    private IdariBirimRepository idariBirimRepository;

    public IdariBirimService(IdariBirimRepository idariBirimRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
        this.idariBirimRepository = idariBirimRepository;
        this.modelMapperServiceImpl = modelMapperServiceImpl;
    }

    @Override
    public ApiResponse save(IdariBirimDTO idariBirimDTO) {
        ApiResponse validationResponse = saveControl(idariBirimDTO);
        if (!validationResponse.getSuccess()) {
            return validationResponse;
        }
        IdariBirim idariBirim = this.modelMapperServiceImpl.request().map(idariBirimDTO, IdariBirim.class);
        idariBirimRepository.save(idariBirim);
        return new ApiResponse(true, "İşlem başarılı.", null);
    }

    public ApiResponse saveControl(IdariBirimDTO idariBirimDTO) {
        if (idariBirimDTO.getName().trim() == null || idariBirimDTO.getName().trim().isEmpty()) {
            return new ApiResponse<>(false, "Hata: İsim boş olamaz.", null);
        }
        return new ApiResponse(true, "İşlem başarılı.", null);
    }

    @Override
    public ApiResponse findAll() {
        List<IdariBirim> birims = idariBirimRepository.findAll();
        List<IdariBirimDTO> dtos = birims.stream().map(birim ->
                this.modelMapperServiceImpl.response().map(birim, IdariBirimDTO.class)).collect(Collectors.toList());
        return new ApiResponse(true, "İşlem başarılı.", dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        IdariBirim idariBirim = idariBirimRepository.findById(id).orElse(null);
        if (idariBirim == null) {
            return new ApiResponse(true, "İdari birim bulunamadı.", null);
        }
        IdariBirimDTO idariBirimDTO = this.modelMapperServiceImpl.response().map(idariBirim, IdariBirimDTO.class);
        return new ApiResponse<>(true, "İşlem başarılı.", idariBirimDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (idariBirimRepository.existsById(id)) {
            idariBirimRepository.deleteById(id);
        }

    }
}
