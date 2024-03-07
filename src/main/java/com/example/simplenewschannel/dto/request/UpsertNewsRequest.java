package com.example.simplenewschannel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
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
