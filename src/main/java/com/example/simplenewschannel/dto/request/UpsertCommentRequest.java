package com.example.simplenewschannel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCommentRequest {
    @NotNull
    private long newsId;
    @NotNull
    private long userId;
    private String commentText;
}
