package com.dk0124.project.comment.application.listing;

import com.dk0124.project.comment.domain.TechArticleComment;
import com.dk0124.project.comment.dto.TechArticleCommentListRequest;

import java.util.List;

public interface TechArticleCommentListUsecase {
    List<TechArticleComment> list(TechArticleCommentListRequest request);

}
