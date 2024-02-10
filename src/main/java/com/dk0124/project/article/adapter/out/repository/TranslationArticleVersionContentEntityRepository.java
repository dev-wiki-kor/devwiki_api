package com.dk0124.project.article.adapter.out.repository;

import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TranslationArticleVersionContentEntityRepository extends JpaRepository<TranslationArticleVersionContentEntity, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE TranslationArticleVersionContentEntity a SET a.viewCount = a.viewCount + 1, a.version_ = a.version_ + 1 WHERE a.articleId = :articleId AND a.version_ = :version_ AND a.version = :version")
    int updateViewCount(@Param("articleId") Long articleId, @Param("version") Long version, @Param("version_") Long version_);


    @Query("SELECT MAX(v.version) FROM TranslationArticleVersionContentEntity v WHERE v.articleId = :articleId GROUP BY v.articleId")
    Long findMaxVersionByArticleId(@Param("articleId") Long articleId);

    void deleteByArticleIdAndVersion(Long articleId, Long version);

    Optional<TranslationArticleVersionContentEntity> findByArticleIdAndVersion(Long articleId, Long version);

}
