package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.NewsRequest;
import com.example.simplenewschannel.dto.response.NewsListResponse;
import com.example.simplenewschannel.dto.response.NewsResponse;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.delegate.NewsMapperDelegate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    NewsResponse newsToResponse(News news);
    News requestToNews(NewsRequest request);

    default NewsListResponse newsListToResponseList(List<News> newsList){
        NewsListResponse newsListResponse = new NewsListResponse();
        newsListResponse.setNewsResponseList(
                newsList.stream().map(this::newsToResponse)
                        .collect(Collectors.toList()));
        return newsListResponse;
    }
}
