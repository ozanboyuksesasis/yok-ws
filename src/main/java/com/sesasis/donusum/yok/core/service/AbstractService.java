package com.sesasis.donusum.yok.core.service;


import com.sesasis.donusum.yok.core.constant.MessageConstant;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.domain.PaginationResponse;
import com.sesasis.donusum.yok.core.domain.SortingAndFilteringDto;
import com.sesasis.donusum.yok.core.exception.ServiceException;
import com.sesasis.donusum.yok.core.payload.ApiMessages;
import com.sesasis.donusum.yok.core.payload.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends BaseModel,R extends JpaRepository> {

    private R repository;

    private final RestTemplate restTemplate = new RestTemplate();

    public AbstractService(R repository)
    {
        this.repository = repository;
    }

    @Transactional
    public ApiResponse createNewInstance(T t){
        try {
            t = (T) repository.save(t);
            return new ApiResponse(true, MessageConstant.INFO,t);
        }
        catch (ServiceException ex)
        {
            return new ApiResponse(false, MessageConstant.ERROR,ex.getMessage());
        }
    }

    public ApiResponse getById(Long id){
        try {
            Optional<T> objOpt = repository.findById(id);
            if (objOpt.isPresent()){
                return new ApiResponse(true, MessageConstant.SUCCESS,objOpt.get());
            }
            return new ApiResponse(false, MessageConstant.INFO, ApiMessages.NO_RECORD_FOUNDS);
        }
        catch (ServiceException ex)
        {
            return new ApiResponse(false, MessageConstant.ERROR,ex.getMessage());
        }
    }

    public ApiResponse delete(Long id){
        try {
            Optional<T> objOpt = repository.findById(id);
            if (objOpt.isPresent()){
                repository.deleteById(id);
                return new ApiResponse(true, MessageConstant.DELETE_MSG,null);
            }
            return new ApiResponse(false, MessageConstant.INFO, ApiMessages.NO_RECORD_FOUNDS);
        }
        catch (ServiceException ex)
        {
            return new ApiResponse(false, MessageConstant.ERROR,ex.getMessage());
        }
    }

    @Transactional
    public void removeById(Long id){
        repository.deleteById(id);
    }

    public ApiResponse getList()
    {
        try{
            List<T> list = repository.findAll();
            return new ApiResponse(true,MessageConstant.INFO,list);
        }
        catch (ServiceException ex)
        {
            return new ApiResponse(false,MessageConstant.ERROR,ex.getMessage());
        }
    }

    public ApiResponse getListWithPaginate(int page, int size, SortingAndFilteringDto sortingAndFilteringDto)
    {
        try{
            PageRequest pageRequest;
            if (sortingAndFilteringDto != null && !sortingAndFilteringDto.getName().isEmpty() && sortingAndFilteringDto.getDirection() != null){
                pageRequest = PageRequest.of(page,size, Sort.by(sortingAndFilteringDto.getDirection(), sortingAndFilteringDto.getName()));
            }else{
                pageRequest = PageRequest.of(page,size);
            }

            Page<T> list = repository.findAll(pageRequest);

            return new ApiResponse(true,MessageConstant.SUCCESS,PaginationResponse.builder()
                    .data(list.getContent())
                    .currentPageNumber(list.getNumber())
                    .totalPageCount(list.getTotalPages())
                    .totalCount(list.getTotalElements())
                    .build());
        }
        catch (ServiceException ex)
        {
            return new ApiResponse(false,MessageConstant.ERROR,ex.getMessage());
        }
    }

    public ApiResponse post(String url, Object request)  {
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(request);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            return new ApiResponse(true,MessageConstant.SUCCESS,response);

        }
        catch (RestClientException ex) {
            return new ApiResponse(false,MessageConstant.ERROR,url);
        }
    }

    public R getRepository() {
        return repository;
    }

    public void setRepository(R repository) {
        this.repository = repository;
    }
}
