package com.onjung.onjung.category.controller;

import com.onjung.onjung.category.domain.Category;
import com.onjung.onjung.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public List<Category> get(){
        return categoryService.findCategories();
    }


}
