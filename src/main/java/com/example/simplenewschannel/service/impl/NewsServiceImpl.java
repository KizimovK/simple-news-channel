package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.repository.NewsRepository;
import com.example.simplenewschannel.service.CategoryService;
import com.example.simplenewschannel.service.NewsService;
import com.example.simplenewschannel.service.UserService;
import com.example.simplenewschannel.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Component
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public NewsServiceImpl(NewsRepository newsRepository,
                           UserService userService,
                           CategoryService categoryService) {
        this.newsRepository = newsRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public News findById(long id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        MessageFormat.format("Новость с такой ID {0} не найдена ", id))
        );
    }

    @Override
    public Page<News> findAll(PageRequest request) {
        return newsRepository.findAll(request);
    }

    @Override
    @Transactional
    public News save(News news, String userName, String categoryName) {
        User author = userService.findByName(userName);
        Category category = categoryService.findByName(categoryName);
        news.setAuthor(author);
        news.setCategory(category);
        author.addNews(news);
        category.addNews(news);
        return newsRepository.save(news);
    }

    @Override
    public News updateNews(News news, long id) {
        News existedNews = findById(id);
        BeanUtils.notNullCopyProperties(news, existedNews);
        return newsRepository.save(existedNews);
    }

    @Override
    public void deleteById(long id) {
        newsRepository.deleteById(id);
    }
}
