package com.example.simplenewschannel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterNewsRequest {
    private String categoryName;
    private String authorName;

}
