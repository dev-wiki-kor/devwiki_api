package com.dk0124.project.comment.domain;

import com.dk0124.project.comment.infrastructure.CommentDeletedReason;
import lombok.Getter;

@Getter
public class TechArticleComment extends Comment {

    private final Long articleId;

    public TechArticleComment(Long articleId, String nickname, Long commentId, String content, Boolean deleted, CommentDeletedReason reason, Long commentOrder, Long level, Long sortNumber, Long parentId, Long writerId) {
        super(commentId, nickname, content, deleted,reason, commentOrder, level, sortNumber, parentId, writerId);
        this.articleId = articleId;
    }

}
