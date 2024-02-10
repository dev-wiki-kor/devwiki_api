package com.dk0124.project.article.domain;

import com.dk0124.project.global.constants.TechTag;

import java.util.Set;

public record TranslationArticleUploadCommand(
        Long authorId,
        String title,
        String content,
        Set<TechTag> techTags
) {
}
