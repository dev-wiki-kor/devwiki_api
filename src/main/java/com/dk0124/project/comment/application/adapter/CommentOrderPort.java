package com.dk0124.project.comment.application.adapter;

public interface CommentOrderPort {

    Long generateNextCommentOrder(Long articleId);

    Long generateNextSortNumberOnLevel(Long articleId, Long commentOrder, Long level);

    void updatePreceedingSortNumber(Long articleId, Long commentOrder, Long parentSortNumber);
}
