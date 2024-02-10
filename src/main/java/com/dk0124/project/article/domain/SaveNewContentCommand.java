package com.dk0124.project.article.domain;

public record SaveNewContentCommand(
        Long editor,
        Long articleId,
        String content,
        Long newVersion,
        Long parentVersion

) {
}
