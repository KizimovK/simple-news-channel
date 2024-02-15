package com.example.simplenewschannel.dto.response;

import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private News news;
    private User user;
    private String commentText;
    private Instant timeCreate;
}
