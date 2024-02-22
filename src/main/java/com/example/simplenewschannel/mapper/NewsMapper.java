package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.dto.response.BriefNewsResponse;
import com.example.simplenewschannel.dto.response.NewsResponse;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.mapper.delegate.NewsMapperDelegate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    NewsResponse newsToResponse(News news);
    News requestToNews(UpsertNewsRequest request);
    BriefNewsResponse newsToBriefResponse(News news);
}
