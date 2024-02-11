package com.dk0124.project.comment.application.create;

import com.dk0124.project.article.exception.InvalidArticleIdException;
import com.dk0124.project.comment.dto.TechArticleCommentCreateRequest;
import com.dk0124.project.comment.exception.InvalidCommentIdException;
import com.dk0124.project.comment.exception.InvalidCommentSizeException;
import com.dk0124.project.comment.infrastructure.TechArticleCommentEntity;
import com.dk0124.project.comment.infrastructure.repository.TechArticleCommentEntityRepository;
import com.dk0124.project.global.config.lock.DistributedLock;
import com.dk0124.project.global.constants.CommentConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TechArticleCommentCreateService implements TechArticleCommentCreateUsecase {

    private final TechArticleCommentEntityRepository commentRepository;


    @Override
    @DistributedLock(key = "'tech_article_comment_order_'+#request.articleId()")
    public void create(TechArticleCommentCreateRequest request, Long userId) {
        validateCreateReq(request);
        if (request.parentCommentId() == null)
            createComment(request, userId);
        else
            createReplyComment(request, userId);
    }

    public void createComment(TechArticleCommentCreateRequest request, Long userId) {
        var order = generateCommentOrder(request.articleId());
        var comment = techCommentReqToEntity(request, order, userId, 0L, 0L);
        commentRepository.save(comment);
    }


    public void createReplyComment(TechArticleCommentCreateRequest request, Long userId) {
        var parent = commentRepository.findById(request.parentCommentId())
                .orElseThrow(InvalidCommentIdException::new);

        var nextSortNum = getNextSortNumber(request.articleId(), parent.getCommentOrder(), parent.getLevel(), parent.getSortNumber());

        updateSortNumberOnPreceedingComments(parent.getArticleId(), parent.getCommentOrder(), nextSortNum);

        commentRepository.save(techCommentReqToEntity(request, parent.getCommentOrder(), userId, parent.getLevel() + 1, nextSortNum));
    }

    private void updateSortNumberOnPreceedingComments(Long articleId, Long commentOrder, Long nextSortNum) {
        commentRepository.incrementNumForSort(articleId, commentOrder, nextSortNum);
    }

    private Long getNextSortNumber(Long articleId, Long commentOrder, Long level, Long parentNumForSort) {
        var numForSort = commentRepository.findMaxSortNumberForLevel(articleId, commentOrder, level + 1);
        return numForSort == null ? parentNumForSort +1 : numForSort + 1;
    }


    private Long generateCommentOrder(Long articleId) {
        var currentMaxOrder = commentRepository.findMaxOrderByArticleId(articleId);
        return currentMaxOrder == null ? 0 : currentMaxOrder + 1;
    }

    private void validateCreateReq(TechArticleCommentCreateRequest request) {

        if (request.articleId() == null)
            throw new InvalidArticleIdException();

        if (request.content() == null || request.content().length() < CommentConstant.COMMENT_MIN_SIZE || request.content().length() > CommentConstant.COMMENT_MAX_SIZE)
            throw new InvalidCommentSizeException();

    }

    private TechArticleCommentEntity techCommentReqToEntity(TechArticleCommentCreateRequest request, Long commentOrder, Long userId, Long level, Long numForSort) {
        return TechArticleCommentEntity.of(
                request.articleId(),
                request.content(),
                commentOrder,
                level,
                numForSort,
                request.parentCommentId(),
                userId
        );
    }
}
