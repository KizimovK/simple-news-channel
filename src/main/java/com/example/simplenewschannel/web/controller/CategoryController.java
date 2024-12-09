package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.CategoryMapper;
import com.example.simplenewschannel.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private  final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public ModelListResponse<CategoryResponse> findAll(@Valid PaginationRequest request){
        Page<Category> categories = categoryService.findAll(request.pageRequest());
        return ModelListResponse.<CategoryResponse>builder()
                .totalCount(categories.getTotalElements())
                .data(categories.stream().map(categoryMapper::categoryToResponse).toList())
                .build();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    public CategoryResponse findById(@PathVariable long categoryId){
        return categoryMapper.categoryToResponse(categoryService.findById(categoryId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public CategoryResponse createNewsCategory(@RequestBody UpsertCategoryRequest request){
        Category categoryNew = categoryService.create(categoryMapper.requestToCategory(request));
        return categoryMapper.categoryToResponse(categoryNew);
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public CategoryResponse updateNewsCategory(@PathVariable long categoryId,
                                               @RequestBody UpsertCategoryRequest request){
        Category categoryNew = categoryService.categoryUpdate(categoryMapper.requestToCategory(request),categoryId);
        return categoryMapper.categoryToResponse(categoryNew);
    }

}
