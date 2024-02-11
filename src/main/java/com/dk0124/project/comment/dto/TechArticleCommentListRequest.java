package com.dk0124.project.comment.dto;

import lombok.NonNull;

public record TechArticleCommentListRequest(
        @NonNull Long articleId,
        Long commentOrder,
        Long sortNumber
) {
}
