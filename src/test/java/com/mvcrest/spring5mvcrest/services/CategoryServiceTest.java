package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mvcrest.spring5mvcrest.domain.Category;
import com.mvcrest.spring5mvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    private static final Long ID = 2L;
    private static final String NAME = "Jimmy";

    CategoryService service;


    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
        service = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoryDTOS = service.getAllCategories();
        assertEquals(3, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO = service.getCategoryByName(NAME);
        assertEquals(NAME, categoryDTO.getName());
        assertEquals(ID, categoryDTO.getId());
    }
}