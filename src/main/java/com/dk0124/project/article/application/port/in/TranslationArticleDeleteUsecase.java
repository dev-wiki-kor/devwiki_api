package com.dk0124.project.article.application.port.in;

import com.dk0124.project.article.adapter.in.TranslationArticleDeleteRequest;

public interface TranslationArticleDeleteUsecase {
    void delete(TranslationArticleDeleteRequest request, Long userId);
}
