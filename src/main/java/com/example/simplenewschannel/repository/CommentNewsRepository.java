package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.CommentNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentNewsRepository extends JpaRepository<CommentNews,Long> {
}
