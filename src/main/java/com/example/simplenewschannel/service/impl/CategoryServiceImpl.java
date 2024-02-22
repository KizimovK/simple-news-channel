package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.exception.EntityExistsException;
import com.example.simplenewschannel.exception.EntityNotFoundException;
import com.example.simplenewschannel.repository.CategoryRepository;
import com.example.simplenewschannel.service.CategoryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Page<Category> findAll(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest);
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
        if (categoryRepository.existsByName(category.getName())){
            throw new EntityExistsException(
                    MessageFormat.format("Такая категория с таким названием, {0}, уже существует"
                            ,category.getName()));
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Категория с таким названием {0} не найдена ", categoryName)));
    }
}
