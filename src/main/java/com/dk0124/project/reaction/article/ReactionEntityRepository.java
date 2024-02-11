package com.dk0124.project.reaction.article;

import com.dk0124.project.global.constants.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionEntityRepository extends JpaRepository<ReactionEntity, Long> {
    Optional<ReactionEntity> findByArticleTypeAndArticleIdAndUserIdAndVersion(ArticleType articleType, Long articleId, Long userId, Long version);
}
