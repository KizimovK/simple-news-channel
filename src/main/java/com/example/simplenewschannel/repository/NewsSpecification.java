package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public interface NewsSpecification {
    static Specification<News> withFilter(FilterNewsRequest filterRequest) {
        return Specification.where(byAuthorName(filterRequest.getAuthorName()))
                .and(byCategoryName(filterRequest.getCategoryName()))
                .and(byCreateAtBefore(filterRequest.getCreateBefore()))
                .and(byUpdateAtBefore(filterRequest.getUpdateBefore()));
    }

    static Specification<News> byUpdateAtBefore(Instant updateBefore) {
        return ((root, query, criteriaBuilder) -> {
            if (updateBefore == null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(News.Fields.timeUpdate), updateBefore);
        });
    }

    static Specification<News> byCreateAtBefore(Instant createBefore) {
        return ((root, query, criteriaBuilder) -> {
            if (createBefore == null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(News.Fields.timeCreate), createBefore);
        });
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
