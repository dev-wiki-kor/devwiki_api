package com.dk0124.project.comment.infrastructure.repository;

import com.dk0124.project.comment.infrastructure.TechArticleCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechArticleCommentEntityRepository extends JpaRepository<TechArticleCommentEntity, Long> {


    @Query("SELECT MAX(v.commentOrder) FROM TechArticleCommentEntity v WHERE v.articleId = :articleId GROUP BY v.articleId")
    Long findMaxOrderByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT MAX(v.sortNumber) from TechArticleCommentEntity v where v.articleId = :articleId and v.commentOrder = :commentOrder and v.level = :level")
    Long findMaxSortNumberForLevel(Long articleId, Long commentOrder, long level);

    @Modifying
    @Query("UPDATE TechArticleCommentEntity a SET a.sortNumber= a.sortNumber + 1 " +
            "WHERE a.articleId = :articleId AND a.commentOrder = :commentOrder AND a.sortNumber>= :sortNumber")
    void incrementNumForSort(@Param("articleId") Long articleId,
                             @Param("commentOrder") Long commentOrder,
                             @Param("sortNumber") Long sortNumber);

}
