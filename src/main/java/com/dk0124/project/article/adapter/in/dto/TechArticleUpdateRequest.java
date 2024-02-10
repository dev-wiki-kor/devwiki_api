package com.dk0124.project.article.adapter.in.dto;

import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.Set;

public record TechArticleUpdateRequest(
        @NonNull Long articleId,
        @Size(min = 1, max = 1000) String title,
        @Size(min = 1, max = 10 * 1024 * 1024) String content,
        Set<String> techTags
) {
}
