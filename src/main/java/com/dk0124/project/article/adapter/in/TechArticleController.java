package com.dk0124.project.article.adapter.in;

import com.dk0124.project.article.adapter.in.dto.TechArticleUpdateRequest;
import com.dk0124.project.article.adapter.in.dto.TechArticleUploadRequest;
import com.dk0124.project.article.application.port.in.TechArticleService;
import com.dk0124.project.article.domain.tech.TechArticleDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/article/tech")
public class TechArticleController {

    private final TechArticleService techArticleService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TechArticleUploadRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        techArticleService.upload(request, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<TechArticleDetail> detail(@PathVariable("articleId") Long articleId) {
        TechArticleDetail detail = techArticleService.detail(articleId);
        return ResponseEntity.ok(detail);
    }

    @PutMapping
    public ResponseEntity<Void>  update(@RequestBody TechArticleUpdateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        techArticleService.update(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> delete(@PathVariable("articleId") Long articleId, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        techArticleService.delete(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(UserDetails userDetails) {
        return Long.valueOf(userDetails.getUsername());
    }
}
