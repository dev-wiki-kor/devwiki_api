package com.dk0124.project.reaction.article;

import com.dk0124.project.article.exception.NoLoginInfoException;
import com.dk0124.project.global.constants.ArticleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reaction/article")
@RequiredArgsConstructor
public class ArticleReactionController {

    private final ArticleReactionService articleReactionService;

    // TechArticle 반응 처리
    @PostMapping("/tech/like")
    public ResponseEntity<Void> likeTechArticle(@PathVariable Long articleId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        articleReactionService.likeArticle(articleId, extractUserId(userDetails), ArticleType.TECH, null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tech/dislike")
    public ResponseEntity<Void> dislikeTechArticle(@PathVariable Long articleId,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        articleReactionService.dislikeArticle(articleId, extractUserId(userDetails), ArticleType.TECH, null);
        return ResponseEntity.ok().build();
    }

    // TranslationArticle 반응 처리
    @PostMapping("/translation/like")
    public ResponseEntity<Void> likeTranslationArticle(@PathVariable Long articleId,
                                                       @PathVariable Long version,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        articleReactionService.likeArticle(articleId, extractUserId(userDetails), ArticleType.TRANSLATION, version);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/translation/dislike")
    public ResponseEntity<Void> dislikeTranslationArticle(@PathVariable Long articleId,
                                                          @PathVariable Long version,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        articleReactionService.dislikeArticle(articleId, extractUserId(userDetails), ArticleType.TRANSLATION, version);
        return ResponseEntity.ok().build();
    }

    // 반응 취소
    @DeleteMapping("/api/reactions/{reactionId}")
    public ResponseEntity<Void> cancelReaction(@PathVariable Long reactionId) {
        articleReactionService.cancelReaction(reactionId);
        return ResponseEntity.ok().build();
    }

    // UserDetails에서 사용자 ID를 추출하는 헬퍼 메서드
    private Long extractUserId(UserDetails userDetails) {
        if (userDetails instanceof UserDetails) {
            return Long.valueOf(userDetails.getUsername());
        }
        throw new NoLoginInfoException();
    }
}
