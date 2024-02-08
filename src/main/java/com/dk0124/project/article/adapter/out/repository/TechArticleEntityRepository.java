package com.dk0124.project.article.adapter.out.repository;

import com.dk0124.project.article.adapter.out.entity.TechArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface TechArticleEntityRepository extends JpaRepository<TechArticleEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE TechArticleEntity a SET a.viewCount = a.viewCount + 1, a.version = a.version + 1 WHERE a.articleId = :articleId AND a.version = :version")
    int updateViewCount(@Param("articleId") Long articleId, @Param("version") Long version);
}
