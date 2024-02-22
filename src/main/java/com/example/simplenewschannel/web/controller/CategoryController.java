package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.CategoryMapper;
import com.example.simplenewschannel.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ModelListResponse<CategoryResponse>> findAll(@Valid PaginationRequest request){
        Page<Category> categories = categoryService.findAll(request.pageRequest());
        return ResponseEntity.ok(ModelListResponse.<CategoryResponse>builder()
                .totalCount(categories.getTotalElements())
                .data(categories.stream().map(categoryMapper::categoryToResponse).toList())
                .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> createNewsCategory(@RequestBody UpsertCategoryRequest request){
        Category categoryNew = categoryService.create(categoryMapper.requestToCategory(request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryNew));
    }

}
