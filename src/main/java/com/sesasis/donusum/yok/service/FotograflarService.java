package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.FotografDTO;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.FotografRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FotograflarService implements IService<FotografDTO> {
    private final FotografRepository fotografRepository;
    private final ModelMapperServiceImpl modelMapperService;

    public FotograflarService(FotografRepository fotografRepository, ModelMapperServiceImpl modelMapperService) {
        this.fotografRepository = fotografRepository;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public ApiResponse save(FotografDTO fotografDTO) {
        ApiResponse response = saveControl(fotografDTO);
        if (response.getSuccess()) {
            return response;
        }
        Fotograf fotograf = this.modelMapperService.request().map(fotografDTO, Fotograf.class);
        fotografRepository.save(fotograf);
        FotografDTO dto = this.modelMapperService.response().map(fotograf, FotografDTO.class);
        return new ApiResponse<>(true,"İşlem başarılı.",dto);
    }

    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
    public  ApiResponse saveControl(FotografDTO fotografDTO) {
        if (fotografDTO.getKurumFotografID() == null && fotografDTO.getImageUrl().trim().isEmpty()){
            return new ApiResponse<>(false,"Hata: Gerekli alanları doldurunuz.",null);
        }
        return new ApiResponse<>(true,"İşlem başarılı.",null);
    }
    public String saveImageAsBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

        return encodedImage;
    }
}
