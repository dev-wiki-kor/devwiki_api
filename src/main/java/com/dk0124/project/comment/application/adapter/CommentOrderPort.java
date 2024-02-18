package com.dk0124.project.comment.application.adapter;

public interface CommentOrderPort {

    Long generateNextCommentOrder(Long articleId);


    Long generateNextSortNumber(Long articleId, Long parentId);

    void updatePrecedingSortNumber(Long articleId, Long commentOrder, Long parentSortNumber);

    void updateChildCountOfSucceedingParents(Long parentId);

}
