package com.example.simplenewschannel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsListResponse {
    List<CommentResponse> commentsRespnseList = new ArrayList<>();
}
