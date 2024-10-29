package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.NewDomainDTO;
import com.sesasis.donusum.yok.dto.NewMenuDTO;
import com.sesasis.donusum.yok.entity.Fotograf;
import com.sesasis.donusum.yok.entity.Menu;
import com.sesasis.donusum.yok.entity.NewDomain;
import com.sesasis.donusum.yok.entity.NewMenu;
import com.sesasis.donusum.yok.mapper.ModelMapperServiceImpl;
import com.sesasis.donusum.yok.repository.FotografRepository;
import com.sesasis.donusum.yok.repository.NewDomainsRepository;
import com.sesasis.donusum.yok.repository.NewMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewMenuService implements IService<NewMenuDTO> {
    private final ModelMapperServiceImpl modelMapperServiceImpl;
    private final NewMenuRepository newMenuRepository;
    private final NewDomainsRepository newDomainsRepository;
    private final FotografRepository fotografRepository;
    @Override
    public ApiResponse save(NewMenuDTO newMenuDTO) {

        Fotograf fotograf = null;
        if (newMenuDTO.getFotografId() != null) {
            fotograf = fotografRepository.findById(newMenuDTO.getFotografId()).orElse(null);
        }

          NewDomain domain = newDomainsRepository.findById(newMenuDTO.getDomainId()).
                orElseThrow(()->new RuntimeException("Domain bulunamadı."));

        NewMenu existMenu = newMenuRepository.findOneByNewDomain_IdAndAnaSayfaMi(domain.getId(), Boolean.TRUE);
        if (existMenu != null && newMenuDTO.isAnaSayfaMi()) {
            return new ApiResponse(false, "Sadece bir tane anasayfa tanımlayabilirsiniz.", null);
        }
        NewMenu menu = this.modelMapperServiceImpl.request().map(newMenuDTO, NewMenu.class);
        menu.setNewDomain(domain);
        menu.setFotograf(fotograf);
        newMenuRepository.save(menu);
        return new ApiResponse(true, MessageConstant.SAVE_MSG, menu.getNewDomain());

    }

    @Override
    public ApiResponse findAll() {
        List< NewMenu > newMenus = newMenuRepository.findAll();
        if (newMenus.isEmpty()){
            return new ApiResponse<>(false,"Liste boş.",null);
        }
        List<NewMenuDTO>  dtos = newMenus.stream().map(menu ->
                this.modelMapperServiceImpl.response().map(menu,NewMenuDTO.class)).collect(Collectors.toList());
        return new ApiResponse(true,"İşlem başarılı.",dtos);
    }

    @Override
    public ApiResponse findById(Long id) {
        NewMenu newMenu = newMenuRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Menü bulunamadı."));

        NewMenuDTO menuDTO = this.modelMapperServiceImpl.response().map(newMenu,NewMenuDTO.class);

        return new ApiResponse<>(true,"İşlem başarılı.",menuDTO);
    }

    @Override
    public void deleteById(Long id) {
    if (newMenuRepository.existsById(id)){
        newMenuRepository.deleteById(id);
    }
    }

    public ApiResponse add(NewMenuDTO newMenuDTO){

        if (newMenuDTO.getFotografId() == null) {
            return new ApiResponse<>(false, "Fotoğraf ID boş olamaz.", null);
        }
        if (newMenuDTO.getId() == null) {
            return new ApiResponse<>(false, "Menü ID boş olamaz.", null);
        }
        Fotograf fotograf = fotografRepository.findById(newMenuDTO.getFotografId())
                .orElseThrow(()-> new RuntimeException("Fotoğraf bulunamadı."));

        NewMenu newMenu = newMenuRepository.findById(newMenuDTO.getId())
                .orElseThrow(()->new RuntimeException("Menü bulunamadı."));
        newMenu.setFotograf(fotograf);
        newMenuRepository.save(newMenu);
       return new ApiResponse<>(true,"Menüye fotoğraf başarılı şekilde eklendi.",null);
    }
}
