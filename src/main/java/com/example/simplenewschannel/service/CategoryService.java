package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.listener.StartExamplesData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    Page<Category> findAll(PageRequest pageRequest);
    Category findById(Long id);
    Category create(Category category);

    Category findByName(String categoryName);
    @ConditionalOnBean(StartExamplesData.class)
    void deleteAll();
}
