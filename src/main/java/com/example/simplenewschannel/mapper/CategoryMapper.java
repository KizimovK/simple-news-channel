package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.UpsertCategoryRequest;
import com.example.simplenewschannel.dto.response.CategoryResponse;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.mapper.delegate.CategoryMapperDelegate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
@DecoratedWith(CategoryMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @Mapping(target = "nameCategory", source = "name")
    CategoryResponse categoryToResponse(Category category);
    Category requestToCategory(UpsertCategoryRequest request);
}
