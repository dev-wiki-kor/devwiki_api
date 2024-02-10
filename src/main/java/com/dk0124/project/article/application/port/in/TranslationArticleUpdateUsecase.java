package com.dk0124.project.article.application.port.in;

import com.dk0124.project.article.adapter.in.dto.TranslationArticleUpdateRequest;

public interface TranslationArticleUpdateUsecase {

    void update(TranslationArticleUpdateRequest request, Long userId);
}
