package com.dk0124.project.article.application.port.out;

import com.dk0124.project.article.domain.translation.TranslationArticleDetail;

public interface TranslationArticleDetailPort {
    TranslationArticleDetail getDetail(Long articleId, Long version);
}
