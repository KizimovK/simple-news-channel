package com.example.simplenewschannel.dto.response;

import com.example.simplenewschannel.entity.CommentNews;
import com.example.simplenewschannel.entity.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long id;

    private String name;

    private String email;

    private List<News> newsUser;

    private List<CommentNews> commentsUser;
}
