package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.dto.response.ExceptionResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.CategoryMapper;
import com.example.simplenewschannel.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category", description = "Category News API")
public class CategoryController {
    private  final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }
    @Operation(
            summary = "Get all category",
            description = "Get all categories news from news-channel"
    )
    @GetMapping
    public ResponseEntity<ModelListResponse<CategoryResponse>> findAll(@Valid PaginationRequest request){
        Page<Category> categories = categoryService.findAll(request.pageRequest());
        return ResponseEntity.ok(ModelListResponse.<CategoryResponse>builder()
                .totalCount(categories.getTotalElements())
                .data(categories.stream().map(categoryMapper::categoryToResponse).toList())
                .build()
        );
    }
    @Operation(
            summary = "Get category by Id",
            description = "Get category by Id. Return id, name category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryService.findById(id)));
    }
    @Operation(
            summary = "Create category news",
            description = "Add category in the news channel"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createNewsCategory(@RequestBody UpsertCategoryRequest request){
        Category categoryNew = categoryService.create(categoryMapper.requestToCategory(request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryNew));
    }

}
