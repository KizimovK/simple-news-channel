package com.example.simplenewschannel.mapper.delegate;

import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.service.CategoryService;
import com.example.simplenewschannel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class NewsMapperDelegate implements NewsMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setAuthor(userService.findById(request.getUserId()));
        news.setCategory(categoryService.findByName(request.getCategoryName()));
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        return news;
    }
}