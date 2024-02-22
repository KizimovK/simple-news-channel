package com.example.simplenewschannel.mapper.delegate;

import com.example.simplenewschannel.dto.response.BriefNewsResponse;
import com.example.simplenewschannel.dto.response.NewsResponse;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.CommentsMapper;
import com.example.simplenewschannel.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private NewsMapper delegate;
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public BriefNewsResponse newsToBriefResponse(News news) {
        BriefNewsResponse briefNewsResponse = delegate.newsToBriefResponse(news);
        briefNewsResponse.setCategoryName(news.getCategory().getName());
        briefNewsResponse.setAuthorName(news.getAuthor().getName());
        briefNewsResponse.setCommentsCount(news.getCommentsList().size());
        return briefNewsResponse;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse newsResponse = delegate.newsToResponse(news);
        newsResponse.setAuthorName(news.getAuthor().getName());
        newsResponse.setCategoryName(news.getCategory().getName());
        newsResponse.setComments(news.getCommentsList().stream()
                .map(commentsMapper::commentToResponse)
                .toList());
        return newsResponse;
    }
}
