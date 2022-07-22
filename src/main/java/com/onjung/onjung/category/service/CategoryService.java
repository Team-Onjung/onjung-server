package com.onjung.onjung.category.service;

import com.onjung.onjung.category.domain.Category;
import com.onjung.onjung.category.dto.CategoryDto;
import com.onjung.onjung.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategories(){
        return categoryRepository.findAll();
    }

}
