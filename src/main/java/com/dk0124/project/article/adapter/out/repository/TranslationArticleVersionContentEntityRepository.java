package com.dk0124.project.article.adapter.out.repository;

import com.dk0124.project.article.adapter.out.entity.TranslationArticleVersionContentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TranslationArticleVersionContentEntityRepository extends JpaRepository<TranslationArticleVersionContentEntity, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE TranslationArticleVersionContentEntity a SET a.viewCount = a.viewCount + 1, a.version_ = a.version_ + 1 WHERE a.articleId = :articleId AND a.version_ = :version_ AND a.version = :version")
    int updateViewCount(@Param("articleId") Long articleId, @Param("version") Long version, @Param("version_") Long version_);


    @Query(value = "SELECT MAX(v.version) FROM article_version_contents v WHERE v.article_id = :articleId GROUP BY v.article_id", nativeQuery = true)
    Long findMaxVersionByArticleId(@Param("articleId") Long articleId);

    // for test
    List<TranslationArticleVersionContentEntity> findAllByArticleId(Long articleId);


    void deleteByArticleIdAndVersion(Long articleId, Long version);

    Long countByArticleId(Long articleId);

    Optional<TranslationArticleVersionContentEntity> findByArticleIdAndVersion(Long articleId, Long version);

}
