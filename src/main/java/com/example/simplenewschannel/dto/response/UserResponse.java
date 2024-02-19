package com.example.simplenewschannel.dto.response;

import com.example.simplenewschannel.entity.Comment;
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
}
