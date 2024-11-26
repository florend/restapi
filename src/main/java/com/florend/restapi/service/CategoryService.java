package com.florend.restapi.service;

import com.florend.restapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(int id);

    CategoryDto addCategory(CategoryDto newCategory);

    CategoryDto updateCategory(int id, CategoryDto newCategory);

    void deleteCategory(int id);
}
