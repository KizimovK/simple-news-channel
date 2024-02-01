package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
