package com.dk0124.project.article.adapter.in;

import com.dk0124.project.article.application.port.in.TranslationArticleDeleteUsecase;
import com.dk0124.project.article.application.port.in.TranslationArticleQuery;
import com.dk0124.project.article.application.port.in.TranslationArticleUpdateUsecase;
import com.dk0124.project.article.application.port.in.TranslationArticleUploadUsecase;
import com.dk0124.project.article.domain.translation.TranslationArticleDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/article/translation")
public class TranslationArticleController {

    private final TranslationArticleQuery translationArticleQuery;
    private final TranslationArticleUploadUsecase translationArticleUpload;
    private final TranslationArticleUpdateUsecase translationArticleUpdate;
    private final TranslationArticleDeleteUsecase translationArticleDelete;


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TranslationArticleUploadRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long uploaderId = extractUserId(userDetails);
        translationArticleUpload.upload(request, uploaderId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}/version/{version}")
    public ResponseEntity<TranslationArticleDetail> detail(@PathVariable Long articleId, @PathVariable Long version, @AuthenticationPrincipal UserDetails userDetails) {
        Long viewerId = extractUserId(userDetails);
        TranslationArticleDetail detail = translationArticleQuery.query(articleId, version);
        return ResponseEntity.ok(detail);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody TranslationArticleUpdateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        translationArticleUpdate.update(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TranslationArticleDeleteRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        translationArticleDelete.delete(request, userId);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(UserDetails userDetails) {
        if(userDetails!=null)
          return Long.valueOf(userDetails.getUsername());
        return null;
    }
}