package com.dk0124.project.comment.dto;

import lombok.NonNull;

public record TechArticleCommentDeleteRequest(
        @NonNull Long commentId
) {
}
