package com.dk0124.project.article.domain.tech;

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
public class TechArticleDetail {
    @NonNull
    private Long articleId;

    @NonNull
    private Author author;

    @NotBlank(message = "Title is required")
    @Size(max = 512)
    private String title;

    @NotNull(message = "Content is required")
    private Content content;

    @NotNull(message = "Tech tags are required")
    private Set<TechTag> techTags;

    @Min(0)
    private int likesCount;
    @Min(0)
    private int dislikesCount;
    @Min(0)
    private int commentCount;

    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private LocalDateTime updatedAt;

}
