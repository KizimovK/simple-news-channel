package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByNewsId(long newsId, Pageable pageable);

    boolean existsByIdAndUserId(long id, long userId);
}
