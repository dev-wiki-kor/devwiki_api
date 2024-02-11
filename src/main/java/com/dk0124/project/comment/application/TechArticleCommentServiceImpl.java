package com.dk0124.project.comment.application;


import com.dk0124.project.article.exception.InvalidUserException;
import com.dk0124.project.comment.dto.TechArticleCommentDeleteRequest;
import com.dk0124.project.comment.exception.CommentNotExistException;
import com.dk0124.project.comment.infrastructure.CommentDeletedReason;
import com.dk0124.project.comment.infrastructure.repository.TechArticleCommentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TechArticleCommentServiceImpl implements TechArticleCommentService{

    private final TechArticleCommentEntityRepository commentRepository;

    @Override
    public void delete(TechArticleCommentDeleteRequest request, Long userId) {

        var comment = commentRepository.findById(request.commentId())
                .orElseThrow(CommentNotExistException::new);

        if(comment.getWriterId() != userId)
            throw new InvalidUserException();

        comment.setDeleted(true);
        comment.setDeletedReason(CommentDeletedReason.DELETED);
    }

}
