package com.example.simplenewschannel.service;

import com.example.simplenewschannel.entity.News;

import java.util.List;

public interface NewsService {
    News findById(long id);
    List<News> findAll();
    News save(News news);

}
