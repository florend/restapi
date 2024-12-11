package com.florend.restapi.service.impl;

import com.florend.restapi.dto.CategoryDto;
import com.florend.restapi.mapper.CategoryMapper;
import com.florend.restapi.model.Category;
import com.florend.restapi.repository.CategoryRepository;
import com.florend.restapi.service.CategoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.florend.restapi.config.CacheConstants.CATEGORIES_CACHE;

@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(CATEGORIES_CACHE)
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper.INSTANCE::categoryToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(int id) {
//        CategoryDto categoryDto = categoryRepository.findById(id);
        return null;
    }

    @Override
    public CategoryDto addCategory(CategoryDto newCategory) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(int id, CategoryDto newCategory) {
        return null;
    }

    @Override
    public void deleteCategory(int id) {

    }
}
