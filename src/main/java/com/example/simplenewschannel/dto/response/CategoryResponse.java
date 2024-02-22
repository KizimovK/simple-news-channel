package com.example.simplenewschannel.dto.response;

import com.example.simplenewschannel.entity.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long id;
    private String nameCategory;
    private int newsCount;
}
