package com.dk0124.project.article.adapter.in.dto;

import lombok.NonNull;

import java.util.Set;

public record TranslationArticleUpdateRequest(
        @NonNull Long articleId,
        @NonNull Long version,
        String content
) {
}
