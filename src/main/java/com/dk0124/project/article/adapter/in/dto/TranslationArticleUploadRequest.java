package com.dk0124.project.article.adapter.in.dto;

import java.util.Set;

public record TranslationArticleUploadRequest(
        String title,
        String content,
        Set<String> techTags
) {
}
