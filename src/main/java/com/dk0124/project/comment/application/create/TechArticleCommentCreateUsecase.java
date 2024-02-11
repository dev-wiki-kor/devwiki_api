package com.dk0124.project.comment.application.create;

import com.dk0124.project.comment.dto.TechArticleCommentCreateRequest;

public interface TechArticleCommentCreateUsecase {

    void create(TechArticleCommentCreateRequest request, Long userId);
}
