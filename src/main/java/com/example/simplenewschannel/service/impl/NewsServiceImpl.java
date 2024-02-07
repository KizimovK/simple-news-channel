package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.repository.NewsRepository;
import com.example.simplenewschannel.service.NewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
@Component
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News findById(long id) {
        return newsRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Новость с такой ID {0} не найдена ",id))
        );
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

}
