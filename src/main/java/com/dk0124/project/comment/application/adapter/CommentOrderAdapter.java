package com.dk0124.project.comment.application.adapter;

import com.dk0124.project.comment.exception.InvalidCommentIdException;
import com.dk0124.project.comment.infrastructure.repository.TechArticleCommentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentOrderAdapter implements CommentOrderPort {

    private final TechArticleCommentEntityRepository commentRepository;

    @Override
    public Long generateNextCommentOrder(Long articleId) {
        Long currentMaxOrder = commentRepository.findMaxOrderByArticleId(articleId);
        return currentMaxOrder != null ? currentMaxOrder + 1 : 0;
    }


    @Override
    public Long generateNextSortNumber(Long articleId, Long parentId) {
        var parent = commentRepository.findById(parentId)
                .orElseThrow(InvalidCommentIdException::new);
        return parent.getSortNumber() + parent.getChildCount() + 1;
    }

    @Override
    public void updatePrecedingSortNumber(Long articleId, Long commentOrder, Long startSortNumber) {
        commentRepository.incrementNumForSort(articleId, commentOrder, startSortNumber);
    }

    @Override
    public void updateChildCountOfSucceedingParents(Long parentId) {
        while (parentId != null) {
            var current = commentRepository.findById(parentId)
                    .orElseThrow(InvalidCommentIdException::new);
            commentRepository.upChildCount(current.getCommentId());
            parentId = current.getParentId();
        }
    }

}
