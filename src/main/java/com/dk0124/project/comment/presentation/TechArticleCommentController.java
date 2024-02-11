package com.dk0124.project.comment.presentation;

import com.dk0124.project.article.exception.NoLoginInfoException;
import com.dk0124.project.comment.application.TechArticleCommentService;
import com.dk0124.project.comment.application.create.TechArticleCommentCreateUsecase;
import com.dk0124.project.comment.application.listing.TechArticleCommentListUsecase;
import com.dk0124.project.comment.domain.TechArticleComment;
import com.dk0124.project.comment.dto.TechArticleCommentCreateRequest;
import com.dk0124.project.comment.dto.TechArticleCommentDeleteRequest;
import com.dk0124.project.comment.dto.TechArticleCommentListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class TechArticleCommentController {

    private final TechArticleCommentService techArticleCommentService;
    private final TechArticleCommentListUsecase techArticleCommentListUsecase;
    private final TechArticleCommentCreateUsecase techArticleCommentCreateUsecase;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        TechArticleCommentDeleteRequest deleteRequest = new TechArticleCommentDeleteRequest(commentId);
        techArticleCommentService.delete(deleteRequest, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TechArticleComment>> listComments(TechArticleCommentListRequest listRequest) {
        List<TechArticleComment> comments = techArticleCommentListUsecase.list(listRequest);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody TechArticleCommentCreateRequest createRequest,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        techArticleCommentCreateUsecase.create(createRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Helper method to extract user ID from UserDetails
    private Long extractUserId(UserDetails userDetails) {

        if (userDetails == null)
            throw new NoLoginInfoException();

        if (userDetails instanceof UserDetails)
            return Long.valueOf(userDetails.getUsername());

        throw new NoLoginInfoException();
    }
}
