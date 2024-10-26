package com.sesasis.donusum.yok.service;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import com.sesasis.donusum.yok.core.service.IService;
import com.sesasis.donusum.yok.dto.CategoryDto;
import com.sesasis.donusum.yok.repository.CategoryRepository;
import com.sesasis.donusum.yok.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements IService<CategoryDto> {

    private final CategoryRepository CategoryRepository;
    private final DomainRepository DomainRepository;

    @Override
    public ApiResponse save(CategoryDto categoryDto) {
        return null;
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
}
