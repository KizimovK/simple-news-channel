package com.example.simplenewschannel.web.controller;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import com.example.simplenewschannel.dto.request.FilterNewsRequest;
import com.example.simplenewschannel.dto.request.PaginationRequest;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.response.*;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.NewsMapper;
import com.example.simplenewschannel.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public ModelListResponse<BriefNewsResponse> filterBy(@Valid PaginationRequest paginationRequest,
                                                                         FilterNewsRequest filterRequest){
        Page<News> filterNews = newsService.filterBy(paginationRequest, filterRequest);
        return ModelListResponse.<BriefNewsResponse>builder()
                .totalCount(filterNews.getTotalElements())
                .data(filterNews.stream().map(newsMapper::newsToBriefResponse).toList())
                .build();
    }

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public NewsResponse findById(@PathVariable long newsId){
        return newsMapper.newsToResponse(newsService.findById(newsId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    public NewsResponse createNews(@RequestBody UpsertNewsRequest request,
                                   @AuthenticationPrincipal UserDetails userDetails){
        News newNews = newsService.save(newsMapper.requestToNews(request)
                ,userDetails.getUsername(), request.getCategoryName());
        return newsMapper.newsToResponse(newNews);
    }

    @PutMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.NEWS)
    public NewsResponse updateNews(@PathVariable long newsId,
                                   @RequestBody UpsertNewsRequest request){
        News updateNews = newsService.updateNews(newsMapper.requestToNews(request), newsId) ;
        return newsMapper.newsToResponse(updateNews);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER')")
    @Accessible(checkBy = AccessType.NEWS, availableForModerator = true)
    public void deleteNewsById(@PathVariable long newsId){
        newsService.deleteById(newsId);
    }


}
