package com.mvcrest.spring5mvcrest.api.v1.mapper;

import com.mvcrest.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mvcrest.spring5mvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
CategoryDTO categoryToCategoryDTO(Category category);
}
