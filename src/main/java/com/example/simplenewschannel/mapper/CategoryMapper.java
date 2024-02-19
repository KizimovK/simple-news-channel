package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryListResponse;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
    default CategoryListResponse categoryListToResponseList(List<Category> categoryList){
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        categoryListResponse.setCategoryListResponse(categoryList.stream()
                .map(this::toCategoryResponse).collect(Collectors.toList()));
        return categoryListResponse;
    }
     Category requestToCategory(UpsertCategoryRequest request);
}
