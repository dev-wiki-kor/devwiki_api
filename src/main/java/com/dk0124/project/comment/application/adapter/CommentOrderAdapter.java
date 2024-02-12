package com.dk0124.project.comment.application.adapter;

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
    public Long generateNextSortNumberOnLevel(Long articleId, Long commentOrder, Long level) {
        Long currentMaxSortNumber = commentRepository.findMaxSortNumberForLevel(articleId, commentOrder, level);
        return currentMaxSortNumber != null ? currentMaxSortNumber + 1 : 0;
    }

    @Override
    public void updatePreceedingSortNumber(Long articleId, Long commentOrder, Long startSortNumber) {
        commentRepository.incrementNumForSort(articleId, commentOrder, startSortNumber);
    }

}
