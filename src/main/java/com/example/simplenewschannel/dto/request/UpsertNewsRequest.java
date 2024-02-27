package com.example.simplenewschannel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {
    @NotNull
    private String authorName;
    @NotNull
    private String categoryName;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
