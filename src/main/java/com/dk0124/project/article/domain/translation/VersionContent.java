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
    private final Long editor;

    @NonNull
    private final String editorNickname;

    @NotNull(message = "Content is required")
    private final Content content;

    private final String versionTitle;

    @Min(0)
    private final int likesCount;
    @Min(0)
    private final int dislikesCount;
    @Min(0)
    private final int commentCount;
    @Min(0)
    private final int views;

    @NonNull
    private final LocalDateTime updatedAt;
}
