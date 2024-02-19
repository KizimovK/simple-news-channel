package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.repository.CategoryRepository;
import com.example.simplenewschannel.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
@Component
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Категория с таким ID {0} не найдена ",id))
        );
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Категория с таким названием {0} не найдена ", categoryName)));
    }
}
