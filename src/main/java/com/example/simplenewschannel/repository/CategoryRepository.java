package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Object> findByCategoryName(String categoryName);
}
