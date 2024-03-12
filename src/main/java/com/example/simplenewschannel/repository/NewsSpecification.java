package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.entity.User;
import org.springframework.data.jpa.domain.Specification;


public interface NewsSpecification {
    static Specification<News> withFilter(FilterNewsRequest filterRequest) {
        return Specification.where(byAuthorName(filterRequest.getAuthorName()))
                .and(byCategoryName(filterRequest.getCategoryName()));
    }

    static Specification<News> byCategoryName(String categoryName) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryName == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(News.Fields.category).get(Category.Fields.name), categoryName);
        });
    }

    static Specification<News> byAuthorName(String authorName) {
        return ((root, query, criteriaBuilder) -> {
            if (authorName == null){
                return null;
            }
            return criteriaBuilder.equal(root.get(News.Fields.author).get(User.Fields.name), authorName);
        });
    }
}
