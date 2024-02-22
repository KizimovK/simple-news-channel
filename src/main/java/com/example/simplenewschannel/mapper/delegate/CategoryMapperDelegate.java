package com.example.simplenewschannel.mapper.delegate;

import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CategoryMapperDelegate implements CategoryMapper {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryResponse categoryToResponse(Category category) {
        CategoryResponse categoryResponse = categoryMapper.categoryToResponse(category);
        categoryResponse.setNewsCount(category.getNewsList().size());
        return categoryResponse;
    }
}
