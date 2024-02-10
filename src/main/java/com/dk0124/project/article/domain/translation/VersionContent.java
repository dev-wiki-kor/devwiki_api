package com.dk0124.project.article.domain.translation;

import com.dk0124.project.article.domain.Content;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor(staticName = "of")
@Getter
public class VersionContent {
    @NonNull
    private final Long editor;

    @NonNull
    private final String editorNickname;

    @NotNull(message = "Content is required")
    private final Content content;

    private final String versionTitle;

    @NonNull
    private final Long version;

    @Min(0)
    private final Long likesCount;
    @Min(0)
    private final Long dislikesCount;
    @Min(0)
    private final Long commentCount;
    @Min(0)
    private final Long views;

    @NonNull
    private final LocalDateTime updatedAt;
}
