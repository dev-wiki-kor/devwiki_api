package com.dk0124.project.article.application;

import com.dk0124.project.article.adapter.in.TranslationArticleUpdateRequest;

public interface TranslationArticleUpdateUsecase {

    void update(TranslationArticleUpdateRequest request, Long userId);
}
