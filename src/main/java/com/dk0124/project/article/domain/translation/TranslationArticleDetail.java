package com.dk0124.project.article.domain.translation;


import com.dk0124.project.article.domain.Author;
import com.dk0124.project.article.domain.Content;
import com.dk0124.project.global.constants.TechTag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
public class TranslationArticleDetail {
    @NonNull
    private String id;

    @NonNull
    private Author author;

    @NotBlank(message = "Title is required")
    @Size(max = 512)
    private String title;

    @NonNull
    private VersionContent versionContent;

    @NotNull(message = "Tech tags are required")// endered for display, e.g., HTML from Markdown
    private Set<TechTag> techTags;

    @Min(0)
    private int totalLikesCount;
    @Min(0)
    private int totalDislikesCount;
    @Min(0)
    private int totalCommentCount;
    @Min(0)
    private int totalViews;

    @NonNull
    private LocalDateTime createdAt;

}