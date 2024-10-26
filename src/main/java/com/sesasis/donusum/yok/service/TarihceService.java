package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.TarihceDTO;
import com.sesasis.donusum.yok.entity.Tarihce;
import com.sesasis.donusum.yok.mapper.ModelMapperService;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.TarihceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarihceService  implements IService<TarihceDTO> {

        private final ModelMapperService modelMapperServiceImpl;
        private final TarihceRepository tarihceRepository;

    public TarihceService(ModelMapperServiceImpl modelMapperServiceImpl, TarihceRepository tarihceRepository) {
        this.modelMapperServiceImpl = modelMapperServiceImpl;
        this.tarihceRepository = tarihceRepository;
    }

         @Override
        public ApiResponse save(TarihceDTO tarihceDTO) {
            Tarihce tarihce =this.modelMapperServiceImpl.request().map(tarihceDTO, Tarihce.class);
            if (tarihce==null){
                return new ApiResponse<>(false,"Kaydetme işlemi başarısız.",null);
            }
            tarihceRepository.save(tarihce);
            return new ApiResponse<>(true,"Kaydetme işlemi başarılı.",null);
        }
        @Override
        public ApiResponse findAll() {
            List<Tarihce> tarihceList = this.tarihceRepository.findAll();
            if (tarihceList.isEmpty()){
                return new ApiResponse<>(false,"Liste getirme işlemi başarısız.", null);
            }
        List<TarihceDTO>   tarihceDTOList =tarihceList.stream().map(tarihce -> this.modelMapperServiceImpl.
                response().map(tarihce, TarihceDTO.class)).collect(Collectors.toList());
            return new ApiResponse<>(true,"Liste başarı ile getirildi.",tarihceDTOList);
        }

        @Override
        public ApiResponse findById(Long id) {
        Tarihce tarihce = this.tarihceRepository.findById(id).orElse(null);
        if (tarihce==null){
            return new ApiResponse<>(false, "Kayıt bulunamadı.", null);
        }
        TarihceDTO tarihceDTO = this.modelMapperServiceImpl.response().map(tarihce, TarihceDTO.class);
        return new ApiResponse<>(true,"Kayıt başarı ile getirildi.",tarihceDTO);
        }

        @Override
        public void deleteById(Long id) {
        if (tarihceRepository.existsById(id)){
            this.tarihceRepository.deleteById(id);
        }
        }

    }
