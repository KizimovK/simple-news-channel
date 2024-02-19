package com.example.simplenewschannel.dto.response;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private long id;
    private User author;
    private Category category;
    private String title;
    private String content;
    private Instant timeCreate;
    List<Comment> commentNewsList;
}
