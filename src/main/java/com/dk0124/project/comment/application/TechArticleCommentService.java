package com.dk0124.project.comment.application;

import com.dk0124.project.comment.dto.TechArticleCommentDeleteRequest;

public interface TechArticleCommentService {
    public void delete(TechArticleCommentDeleteRequest request, Long userId);
}
