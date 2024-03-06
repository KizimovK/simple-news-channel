package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    boolean existsByIdAndAuthorId(long id, long authorId);
    @Query("SELECT id FROM news")
    List<Long> findAllId();

}
