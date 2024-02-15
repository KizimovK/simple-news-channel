package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category create(Category category);

    Category findByName(String categoryName);
}
