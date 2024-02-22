package com.example.simplenewschannel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private long id;
    private String authorName;
    private String categoryName;
    private String title;
    private String content;
    private Instant timeCreate;
    private Instant timeUpdate;
    List<CommentResponse> comments = new ArrayList<>();
}
