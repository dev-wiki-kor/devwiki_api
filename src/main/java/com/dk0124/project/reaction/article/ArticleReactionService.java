package com.dk0124.project.reaction.article;

import com.dk0124.project.global.constants.ArticleType;
import com.dk0124.project.reaction.ReactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleReactionService {

    private final ReactionEntityRepository reactionRepository;

    public void likeArticle(Long articleId, Long userId, ArticleType articleType, Long version) {
        updateOrSaveArticleReaction(ReactionType.LIKE, articleId, userId, articleType, version);
    }

    public void dislikeArticle(Long articleId, Long userId, ArticleType articleType, Long version) {
        updateOrSaveArticleReaction(ReactionType.DISLIKE, articleId, userId, articleType, version);
    }

    public void cancelReaction(Long reactionId) {
        reactionRepository.deleteById(reactionId);
    }

    private void updateOrSaveArticleReaction(ReactionType reactionType, Long articleId, Long userId, ArticleType articleType, Long version) {
        Optional<ReactionEntity> reaction =
                reactionRepository.findByArticleTypeAndArticleIdAndUserIdAndVersion(articleType, articleId, userId, version);

        if (reaction.isPresent()) {
            reaction.get().setReactionType(reactionType);
        } else {
            saveArticleReaction(reactionType, articleId, userId, articleType, version);
        }
        reactionRepository.save(reaction.get());
    }

    private void saveArticleReaction(ReactionType reactionType, Long articleId, Long userId, ArticleType articleType, Long version) {
        ReactionEntity newReaction = ReactionEntity.of(
                reactionType,
                articleType,
                articleId,
                version,  // version might be null for TECH articles
                userId
        );
        reactionRepository.save(newReaction);
    }
}