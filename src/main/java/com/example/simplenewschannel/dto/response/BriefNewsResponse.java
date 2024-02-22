package com.example.simplenewschannel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BriefNewsResponse {
    private long id;
    private String authorName;
    private String categoryName;
    private String title;
    private String content;
    private Instant timeCreate;
    private Instant timeUpdate;
    private int commentsCount;
}
