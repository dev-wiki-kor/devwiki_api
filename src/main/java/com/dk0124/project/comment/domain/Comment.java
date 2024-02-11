package com.dk0124.project.comment.domain;

import com.dk0124.project.comment.infrastructure.CommentDeletedReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Comment {
    private final Long commentId;
    private final String nickname;
    private final String content;
    private final Boolean deleted ;
    private final CommentDeletedReason deletedReason;
    private final Long commentOrder;
    private final Long level;
    private final Long sortNumber;
    private final Long parentId;
    private final Long writerId;
}
