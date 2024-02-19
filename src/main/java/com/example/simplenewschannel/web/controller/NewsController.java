package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.response.NewsListResponse;
import com.example.simplenewschannel.dto.response.NewsResponse;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    public NewsController(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(){
        return ResponseEntity.ok(newsMapper.newsListToResponseList(newsService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }
    @PostMapping
    public ResponseEntity<NewsResponse> createNews(@RequestBody UpsertNewsRequest request){
        News newNews = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }


}
