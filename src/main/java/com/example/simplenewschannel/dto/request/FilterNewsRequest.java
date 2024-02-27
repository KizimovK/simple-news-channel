package com.example.simplenewschannel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterNewsRequest {
    private String categoryName;
    private String authorName;
    private Instant createBefore;
    private Instant updateBefore;
}
