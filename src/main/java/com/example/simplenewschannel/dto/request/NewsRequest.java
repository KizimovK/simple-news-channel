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
    private User user;
    private Category category;
    private String title;
    private String content;
}
