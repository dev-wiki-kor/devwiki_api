package com.dk0124.project.article.domain.translation;

import com.dk0124.project.article.domain.Content;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class VersionContent {
    @NonNull
    private Long editor;

    @NonNull
    private String editorNickname;

    @NotNull(message = "Content is required")
    private Content content;

    @Min(0)
    private int likesCount;
    @Min(0)
    private int dislikesCount;
    @Min(0)
    private int commentCount;
    @Min(0)
    private int views;

    @NonNull
    private LocalDateTime updatedAt;
}
