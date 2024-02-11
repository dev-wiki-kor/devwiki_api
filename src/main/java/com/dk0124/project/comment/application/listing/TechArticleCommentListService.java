package com.dk0124.project.comment.application.listing;

import com.dk0124.project.comment.domain.CommentCursor;
import com.dk0124.project.comment.domain.TechArticleComment;
import com.dk0124.project.comment.dto.TechArticleCommentListRequest;
import com.dk0124.project.comment.infrastructure.repository.TechArticleCommentListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TechArticleCommentListService implements TechArticleCommentListUsecase {

    private final TechArticleCommentListRepository commentListRepository;

    @Override
    public List<TechArticleComment> list(TechArticleCommentListRequest request) {
        if (request.commentOrder() == null || request.sortNumber() == null)
            return commentListRepository.findTechArticleCommentsWithCursor(
                    request.articleId(), new CommentCursor(0L, 0L), 50
            );

        return commentListRepository.findTechArticleCommentsWithCursor(
                request.articleId(), new CommentCursor(request.commentOrder(), request.sortNumber()), 50
        );
    }
}
