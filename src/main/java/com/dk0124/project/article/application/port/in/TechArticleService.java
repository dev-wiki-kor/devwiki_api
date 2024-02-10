package com.dk0124.project.article.application.port.in;

import com.dk0124.project.article.adapter.in.TechArticleUpdateRequest;
import com.dk0124.project.article.adapter.in.TechArticleUploadRequest;
import com.dk0124.project.article.domain.tech.TechArticleDetail;

public interface TechArticleService {

    void upload(TechArticleUploadRequest techArticleUploadRequest, Long userId);

    void update(TechArticleUpdateRequest techArticleUpdateRequest, Long userId);

    void delete(Long articleId, Long userId);

    TechArticleDetail detail(Long articleId);
}
