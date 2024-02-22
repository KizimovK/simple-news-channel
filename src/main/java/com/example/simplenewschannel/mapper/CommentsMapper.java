package com.example.simplenewschannel.mapper;

import com.example.simplenewschannel.dto.response.CommentResponse;
import com.example.simplenewschannel.dto.response.CommentsListResponse;
import com.example.simplenewschannel.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentsMapper {

    CommentResponse commentToResponse(Comment comment);
    default CommentsListResponse commentsListToResponseList(List<Comment> commentsList){
        CommentsListResponse commentsListResponse = new CommentsListResponse();
        commentsListResponse.setCommentsRespnseList(commentsList.stream()
                .map(this::commentToResponse).collect(Collectors.toList()));
        return commentsListResponse;
    }
}
