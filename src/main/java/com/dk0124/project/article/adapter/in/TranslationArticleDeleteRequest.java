package com.dk0124.project.article.adapter.in;

import lombok.NonNull;

public record TranslationArticleDeleteRequest(
        @NonNull Long articleId,
        @NonNull Long version
) {
}
