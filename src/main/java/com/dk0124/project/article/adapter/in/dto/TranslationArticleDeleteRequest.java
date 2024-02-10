package com.dk0124.project.article.adapter.in.dto;

import lombok.NonNull;

public record TranslationArticleDeleteRequest(
        @NonNull Long articleId,
        @NonNull Long version
) {
}
