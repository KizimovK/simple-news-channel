package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.CategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryListResponse;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.CategoryMapper;
import com.example.simplenewschannel.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private  final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(){
        return ResponseEntity.ok((categoryMapper.categoryListToResponseList(categoryService.findAll())));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(categoryService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> createNewsCategory(@RequestBody CategoryRequest request){
        Category categoryNew = categoryService.create(categoryMapper.requestToCategory(request));
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(categoryNew));
    }

}
