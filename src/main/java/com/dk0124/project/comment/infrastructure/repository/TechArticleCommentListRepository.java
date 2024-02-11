package com.dk0124.project.comment.infrastructure.repository;


import com.dk0124.project.comment.domain.CommentCursor;
import com.dk0124.project.comment.domain.TechArticleComment;
import com.dk0124.project.comment.infrastructure.QTechArticleCommentEntity;
import com.dk0124.project.user.adapter.out.user.entity.QUserProfileEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechArticleCommentListRepository {
    private final JPAQueryFactory queryFactory;


    public List<TechArticleComment> findTechArticleCommentsWithCursor(Long articleId, CommentCursor cursor, int limit) {
        QTechArticleCommentEntity qComment = QTechArticleCommentEntity.techArticleCommentEntity;
        QUserProfileEntity qUser = QUserProfileEntity.userProfileEntity;

        // 커서 조건 생성
        BooleanExpression cursorCondition = qComment.commentOrder.gt(cursor.getCommentOrder())
                .or(qComment.commentOrder.eq(cursor.getCommentOrder())
                        .and(qComment.numForSort.gt(cursor.getNumForSort())));

        return queryFactory
                .select(Projections.constructor(TechArticleComment.class,
                        qComment.articleId,
                        qUser.nickname,
                        qComment.commentId,
                        qComment.content,
                        qComment.deleted,
                        qComment.commentDeletedReason,
                        qComment.commentOrder,
                        qComment.level,
                        qComment.numForSort,
                        qComment.parentId,
                        qComment.writerId
                      )
                )
                .from(qComment)
                .join(qUser).on(qComment.writerId.eq(qUser.userMetaId))
                .where(qComment.articleId.eq(articleId)
                        .and(cursorCondition))
                .orderBy(qComment.commentOrder.asc(), qComment.numForSort.asc())
                .limit(limit)
                .fetch();
    }
}
