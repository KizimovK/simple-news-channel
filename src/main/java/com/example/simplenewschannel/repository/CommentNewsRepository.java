package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentNewsRepository extends JpaRepository<Comment,Long> {
}
