package com.dk0124.project.comment.dto;

import lombok.NonNull;

public record TechArticleCommentCreateRequest(
        Long articleId,
        Long parentCommentId,
        String content
) {
}
