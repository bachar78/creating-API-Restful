package com.mvcrest.spring5mvcrest.controllers.v1;

import com.mvcrest.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mvcrest.spring5mvcrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    public static final String NAME = "Jim";

    @InjectMocks
    CategoryController controller;

    @Mock
    CategoryService service;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName("Bachar");
        categoryDTO1.setId(1L);
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO1.setName("Jim");
        categoryDTO1.setId(2L);
        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);
        when(service.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/api/v1/categories/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    void getCategoryByName() throws Exception {
        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setName(NAME);
        when(service.getCategoryByName(anyString())).thenReturn(category);
        mockMvc.perform(get("/api/v1/categories/Jim")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }
}