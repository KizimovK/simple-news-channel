package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentsMapper {
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "news.id", target = "newsId")
    CommentResponse commentToResponse(Comment comment);
    Comment requestToComment(UpsertCommentRequest request);
}
