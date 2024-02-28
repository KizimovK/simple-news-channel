package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.response.BriefNewsResponse;
import com.example.simplenewschannel.dto.response.ModelListResponse;
import com.example.simplenewschannel.dto.response.NewsResponse;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
//    @GetMapping
//    public ResponseEntity<ModelListResponse<BriefNewsResponse>> findAll(@Valid PaginationRequest request){
//        Page<News> allNews = newsService.findAll(request.pageRequest());
//        return ResponseEntity.ok(ModelListResponse.<BriefNewsResponse>builder()
//                .totalCount(allNews.getTotalElements())
//                .data(allNews.stream().map(newsMapper::newsToBriefResponse).toList())
//                .build());
//    }
    @GetMapping
    public ResponseEntity<ModelListResponse<BriefNewsResponse>> filterBy(@Valid PaginationRequest paginationRequest,
                                                                         FilterNewsRequest filterRequest){
        Page<News> filterNews = newsService.filterBy(paginationRequest, filterRequest);
        return ResponseEntity.ok(ModelListResponse.<BriefNewsResponse>builder()
                .totalCount(filterNews.getTotalElements())
                .data(filterNews.stream().map(newsMapper::newsToBriefResponse).toList())
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> createNews(@RequestBody UpsertNewsRequest request){
        News newNews = newsService.save(newsMapper.requestToNews(request)
                ,request.getAuthorName(),request.getCategoryName());
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }
    @Accessible(checkBy = AccessType.NEWS)
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable long id,
                                                   @RequestBody UpsertNewsRequest request){
        News updateNews = newsService.updateNews(newsMapper.requestToNews(request), id);
        return ResponseEntity.ok(newsMapper.newsToResponse(updateNews));
    }
    @Accessible(checkBy = AccessType.NEWS)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsById(@PathVariable long id, long userId){
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
