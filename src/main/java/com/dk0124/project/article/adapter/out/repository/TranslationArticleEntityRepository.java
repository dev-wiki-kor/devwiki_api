package com.dk0124.project.article.adapter.out.repository;

import com.dk0124.project.article.adapter.out.entity.TranslationArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TranslationArticleEntityRepository extends JpaRepository<TranslationArticleEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE TranslationArticleEntity a SET a.viewCount = a.viewCount + 1, a.version_ = a.version_ + 1 WHERE a.articleId = :articleId AND a.version_ = :version_")
    int updateViewCount(@Param("articleId") Long articleId, @Param("version_") Long version_);



}
