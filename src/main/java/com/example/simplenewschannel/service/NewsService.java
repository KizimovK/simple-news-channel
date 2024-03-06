package com.example.simplenewschannel.service;

import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NewsService {
    News findById(long id);
    Page<News> findAll(PageRequest pageRequest);
    News save(News news, String userName, String categoryName);

    News updateNews(News news, long id);

    void deleteById(long id);

    Page<News> filterBy(PaginationRequest paginationRequest, FilterNewsRequest filterRequest);
    List<Long> getAllIdNews();
}
