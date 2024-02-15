package com.example.simplenewschannel.dto.request;

import com.example.simplenewschannel.entity.Category;
import com.example.simplenewschannel.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {

   //??????????????????
    private long userId;
    private String categoryName;
    private String title;
    private String content;
}
