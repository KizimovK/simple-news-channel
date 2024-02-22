package com.example.simplenewschannel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {

    private String authorName;
    private String categoryName;
    private String title;
    private String content;
}
